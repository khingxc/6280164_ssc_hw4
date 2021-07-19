package io.muic.ssc.webapp;

import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;
import io.muic.ssc.webapp.servlets.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;

import java.io.File;

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

            //customize error page for redirection
            ErrorPage error404Page = new ErrorPage();
            error404Page.setErrorCode(404);
            error404Page.setLocation("/WEB-INF/error404.jsp");
            ctx.addErrorPage(error404Page);

            tomcat.start();
            tomcat.getServer().await();
        }
        catch (LifecycleException e){
            e.printStackTrace();
        }
    }

}
