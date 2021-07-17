package io.muic.ssc.webapp;

import io.muic.ssc.webapp.servlets.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;

public class Webapp {

    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(61083);

        File docbase = new File("src/main/webapp");
        docbase.mkdirs();


        try{
            Context ctx = tomcat.addWebapp("", docbase.getAbsolutePath());

            ServletRouter servletRouter = new ServletRouter();
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        }
        catch (ServletException | LifecycleException e){
            e.printStackTrace();
        }
    }

}
