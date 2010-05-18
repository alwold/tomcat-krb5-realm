package edu.asu.wmac.tomcat.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import com.sun.security.auth.module.Krb5LoginModule;

/**
 * Extended version of the Krb5LoginModule which adds role principals to the
 * Subject object.  See 
 * http://jakarta.apache.org/tomcat/tomcat-5.0-doc/catalina/docs/api/org/apache/catalina/realm/JAASRealm.html
 * 
 * @author alwold
 */
public class Krb5TomcatLoginModule extends Krb5LoginModule {
   private Subject subject;
   private List users;

   public Krb5TomcatLoginModule() {
      super();
      try {
         String config = System.getProperty("catalina.home")+"/conf/krb5-users.conf";
         BufferedReader br = new BufferedReader(new FileReader(config));
         users = new Vector();
         while(br.ready()) {
            users.add(br.readLine());
         }
      } catch (Exception e) {
         // print out the error, and no one will have any roles
         e.printStackTrace();
      }
   }

   /**
    * Call the initialize method on the parent then keep a handle
    * to the subject, in order to add principals later.
    */
   public void initialize(Subject subject, CallbackHandler callbackHandler,
                          Map sharedState, Map options) {
      super.initialize(subject, callbackHandler, sharedState, options);
      this.subject = subject;
   }

   /**
    * Once the commit is run on the parent, the Subject should be populated so
    * we can check who is logged in and add the appropriate roles.
    */
   public boolean commit() throws LoginException {
      boolean rval = super.commit();
      if (rval) {
         Set principals = subject.getPrincipals();
         for (Iterator i = principals.iterator(); i.hasNext(); ) {
            Principal p = (Principal)i.next();
            if (users.contains(p.getName())) {
               principals.add(new RolePrincipal("manager"));
               principals.add(new RolePrincipal("admin"));
               // once access is granted, we can bail
               break;
            }
         }
      }
      return rval;
   }
}
