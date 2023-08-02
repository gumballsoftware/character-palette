package org.gumball.fun.disneyapi;

import java.util.ArrayList;

public class PageData {
   PageInfo InfoObject;
   ArrayList< Character > data = new ArrayList < Character > ();

   // Getter Methods

   public PageInfo getInfo() {
      return InfoObject;
   }

   public PageInfo getInfoObject() {
      return InfoObject;
   }

   public void setInfoObject(PageInfo infoObject) {
      InfoObject = infoObject;
   }

   public ArrayList<Character> getData() {
      return data;
   }

   public void setData(ArrayList<Character> data) {
      this.data = data;
   }

   public void setInfo(PageInfo infoObject) {
      this.InfoObject = infoObject;
   }
}
