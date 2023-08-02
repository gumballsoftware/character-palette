package org.gumball.fun.disneyapi;

import org.w3c.dom.css.RGBColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Character {
   Integer _id;
   List<String> films;
   List<String> shortFilms;
   List<String> tvShows;
   List<String> videoGames;
   List<String> parkAttractions;
   List<String> allies;
   List<String> enemies;
   String sourceUrl;
   String imageUrl;
   String name;
   String url;

   List<String> colorPalette = new ArrayList<>();
   int[][] rgbpalette;

   public void setColors(int[][] palette) {
      rgbpalette = palette;
   }

   public void addColor(String name) {
      getColorPalette().add(name);
   }
   public List<String> getColorPalette() {
      return colorPalette;
   }

   public int[][] getRgbpalette() {
      return rgbpalette;
   }

   public void set_id(Integer _id) {
      this._id = _id;
   }

   public List<String> getFilms() {
      return films;
   }

   public void setFilms(List<String> films) {
      this.films = films;
   }

   public List<String> getShortFilms() {
      return shortFilms;
   }

   public void setShortFilms(List<String> shortFilms) {
      this.shortFilms = shortFilms;
   }

   public List<String> getTvShows() {
      return tvShows;
   }

   public void setTvShows(List<String> tvShows) {
      this.tvShows = tvShows;
   }

   public List<String> getVideoGames() {
      return videoGames;
   }

   public void setVideoGames(List<String> videoGames) {
      this.videoGames = videoGames;
   }

   public List<String> getParkAttractions() {
      return parkAttractions;
   }

   public void setParkAttractions(List<String> parkAttractions) {
      this.parkAttractions = parkAttractions;
   }

   public List<String> getAllies() {
      return allies;
   }

   public void setAllies(List<String> allies) {
      this.allies = allies;
   }

   public List<String> getEnemies() {
      return enemies;
   }

   public void setEnemies(List<String> enemies) {
      this.enemies = enemies;
   }

   public String getSourceUrl() {
      return sourceUrl;
   }

   public void setSourceUrl(String sourceUrl) {
      this.sourceUrl = sourceUrl;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   @Override
   public String toString() {
      return "Character{" +
         "_id=" + _id +
         ", name='" + name + '\'' +
         '}';
   }
}
