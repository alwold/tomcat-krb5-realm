Tomcat Krb5 Login Module
------------------------
This module allows you to authenticate users using Kerberos to tomcat.  It is designed to be used for
the manager and admin applications.  It extends the Sun Krb5LoginModule class, so it requires a 1.4+
Sun JRE.  It works exactly the same as the Sun version, with the addition of tomcat's hacked authorization
scheme.

To compile, run ant dist, and you will get a krb5-tomcat.jar file.  Put it in /usr/local/tomcat/lib.
Set up the realm according to http://tomcat.apache.org/tomcat-6.0-doc/realm-howto.html#JAASRealm.
The user class name is "javax.security.auth.kerberos.KerberosPrincipal" and the role class name is
"edu.asu.wmac.tomcat.security.RolePrincipal".  In your LoginModule config, set the class name to
"edu.asu.wmac.tomcat.security.Krb5TomcatLoginModule", and use the options documented for the 
Krb5LoginModule.

To authorize users, put the full principal name (such as alwold@ASU.EDU) in a file called 
/usr/local/tomcat/conf/krb5-users.conf.  Put one user on each line.
