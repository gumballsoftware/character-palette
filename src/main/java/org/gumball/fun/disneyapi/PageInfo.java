package org.gumball.fun.disneyapi;
public class PageInfo {
   private int count;
   private int totalPages;
   private String previousPage;
   private String nextPage;


   // Getter Methods

   public int getCount() {
      return count;
   }

   public int getTotalPages() {
      return totalPages;
   }

   public String getPreviousPage() {
      return previousPage;
   }

   public String getNextPage() {
      return nextPage;
   }

   // Setter Methods

   public void setCount(int count) {
      this.count = count;
   }

   public void setTotalPages(int totalPages) {
      this.totalPages = totalPages;
   }

   public void setPreviousPage(String previousPage) {
      this.previousPage = previousPage;
   }

   public void setNextPage(String nextPage) {
      this.nextPage = nextPage;
   }
}