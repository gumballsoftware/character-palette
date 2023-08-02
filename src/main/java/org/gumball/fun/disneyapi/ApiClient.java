package org.gumball.fun.disneyapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.androidpit.colorthief.ColorThief;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ApiClient {
   int pagesize = 20;
   String baseUrl = "http://api.disneyapi.dev/xxx";
   HttpClient httpClient;
   static ObjectMapper objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

   ExecutorService executor = Executors.newFixedThreadPool(15);

   public ApiClient(int pagesize, String baseUrl) {
      this.pagesize = pagesize;
      this.baseUrl = baseUrl;
      httpClient = HttpClient.newBuilder().executor(executor).build();
   }

   public CompletableFuture<PageData> getPage(int page) throws IOException, InterruptedException, ExecutionException {

      System.err.println("getPage " + Thread.currentThread().getId());
      String params = "?page=" + page ;

      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(baseUrl + params))
         .header("content-type", "application/json")
         .GET()
         .build();

      CompletableFuture<PageData> retval = httpClient
         .sendAsync(request, HttpResponse.BodyHandlers.ofString())
         .thenApply(HttpResponse::body)
         .thenApply(ApiClient::readValueJackson)
         .thenApply(this::fetchImages)
         .thenApply(this::fetchColors);

      return retval;
   }

   public void go() {
      System.err.println("go " + Thread.currentThread().getId());
      int page = 1;
      int characterCount = 0;
      boolean hasNextPage = true;
      List<CompletableFuture<PageData>> pageFutures = new ArrayList<>();
      try {
         CompletableFuture<PageData> pageData = getPage(page);
         PageData pageData1 = pageData.get();
         int pageCount = pageData1.getInfo().getTotalPages();
//         int pageCount = 2;
         while ( (pageData != null) && (page < pageCount)){
            page = ++page;
            pageData = getPage(page);
            pageFutures.add(pageData);
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      } catch (ExecutionException e) {
         throw new RuntimeException(e);
      }
      try {
         CompletableFuture.allOf(pageFutures.toArray(new CompletableFuture[0])).get();

         for (CompletableFuture<PageData> pd : pageFutures) {
            characterCount = characterCount + pd.join().data.size();
         }

         System.err.println("done.  # Characters: " + characterCount);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      } catch (ExecutionException e) {
         throw new RuntimeException(e);
      }
   }

   static PageData readValueJackson(String content) {
      try {
         System.err.println("readValueJackson " + Thread.currentThread().getId());
         return objectMapper.readValue(content, new TypeReference<PageData>(){});
      } catch (IOException ioe) {
         throw new CompletionException(ioe);
      }
   }

   PageData fetchImages(PageData pageData) {
      pageData.data.stream().forEach( character -> {
         if (character.imageUrl == null) {
            System.err.println("no imageURL for " + character);
            return;
         }

         HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(character.imageUrl))
            .GET()
            .build();
         try {
            HttpResponse<byte[]> imageResponse =
               httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageResponse.body()));
            int[][] palette = ColorThief.getPalette(image, 3, 1, true);
            character.setColors(palette);
         } catch (IOException e) {
            throw new RuntimeException(e);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      });
      return pageData;
   }

   String fetchColorName(int r, int g, int b) {
      String retval = "black";
      String colorAPI = "https://www.thecolorapi.com/id?format=json&rgb="+r+","+g+","+b;
      HttpRequest request = HttpRequest.newBuilder()
         .uri(URI.create(colorAPI))
         .header("content-type", "application/json")
         .GET()
         .build();
      String json = null;
      try {
         json = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      Color color = null;
      try {
         color = objectMapper.readValue(json, new TypeReference<Color>(){});
         retval = color.name.value;
      } catch (JsonProcessingException e) {
         throw new RuntimeException(e);
      }

      return retval;
   }

   PageData fetchColors(PageData pageData) {
      pageData.data.stream().forEach( character -> {
         int[][] palette = character.getRgbpalette();
         for (int i=0; i<palette.length; i++) {
            int[] rgb = palette[i];
            String name = fetchColorName(rgb[0], rgb[1], rgb[2]);
            character.addColor(name);
         }
      });
      return pageData;
   }
}