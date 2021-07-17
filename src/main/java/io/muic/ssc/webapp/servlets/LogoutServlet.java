package io.muic.ssc.webapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        securityService.logout(req);
        resp.sendRedirect("/");
    }

    @Override
    public String getPattern() {
        return "/logout";
    }
}
