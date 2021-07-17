package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.service.SecurityService;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRoutableHttpServlet extends HttpServlet implements Routable{

    protected SecurityService securityService;

    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

}
