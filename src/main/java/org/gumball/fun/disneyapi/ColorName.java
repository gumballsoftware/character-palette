package org.gumball.fun.disneyapi;

public class ColorName {
   String value;
   String closest_named_hex;
   boolean exact_match_name;
   int distance;

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getClosest_named_hex() {
      return closest_named_hex;
   }

   public void setClosest_named_hex(String closest_named_hex) {
      this.closest_named_hex = closest_named_hex;
   }

   public boolean isExact_match_name() {
      return exact_match_name;
   }

   public void setExact_match_name(boolean exact_match_name) {
      this.exact_match_name = exact_match_name;
   }

   public int getDistance() {
      return distance;
   }

   public void setDistance(int distance) {
      this.distance = distance;
   }
}
