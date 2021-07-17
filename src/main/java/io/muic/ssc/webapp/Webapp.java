package io.muic.ssc.webapp;

import io.muic.ssc.webapp.service.DatabaseConnectionService;
import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;
import io.muic.ssc.webapp.servlets.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.security.Security;

public class Webapp {

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(61083);

        File docbase = new File("src/main/webapp");
        docbase.mkdirs();

        SecurityService securityService = new SecurityService();
        securityService.setUserService(UserService.getInstance());

        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        try{
            Context ctx = tomcat.addWebapp("", docbase.getAbsolutePath());

            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        }
        catch (ServletException | LifecycleException e){
            e.printStackTrace();
        }
    }

}
