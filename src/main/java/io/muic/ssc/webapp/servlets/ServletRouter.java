package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.util.ArrayList;
import java.util.List;

public class ServletRouter {

    private final List<Class<? extends AbstractRoutableHttpServlet>> servletClasses = new ArrayList<>();

     {

        servletClasses.add(HomeServlet.class);
        servletClasses.add(LoginServlet.class);
        servletClasses.add(LogoutServlet.class);

     }

    public void init(Context ctx){
         UserService userService = new UserService();
         SecurityService securityService = new SecurityService();
         securityService.setUserService(userService);

         for (Class<? extends AbstractRoutableHttpServlet> servletClass: servletClasses){
             try{
                 AbstractRoutableHttpServlet httpServlet = servletClass.newInstance();
                 httpServlet.setSecurityService(securityService);
                 Tomcat.addServlet(ctx, servletClass.getSimpleName(), httpServlet);
                 ctx.addServletMapping(httpServlet.getPattern(), servletClass.getSimpleName());
                }
             catch (InstantiationException e){
                 e.printStackTrace();
             }
             catch(IllegalAccessException e) {
                 e.printStackTrace();
             }
         }

    }

}
