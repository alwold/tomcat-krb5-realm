package edu.asu.wmac.tomcat.security;

import java.security.Principal;

public class RolePrincipal implements Principal {
   private String name;

   public RolePrincipal(String name) {
      this.name = name;
   }
   
   public String getName() {
      return name;
   }

   public boolean equals(Object o) {
      if (o instanceof RolePrincipal) {
         if (((RolePrincipal)o).getName().equals(name)) {
            return true;
         }
      }
      return false;
   }

   public String toString() {
      return name;
   }
}
