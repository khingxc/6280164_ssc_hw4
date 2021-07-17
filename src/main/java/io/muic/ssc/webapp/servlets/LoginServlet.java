package io.muic.ssc.webapp.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        RequestDispatcher reqDispatcher = req.getRequestDispatcher("WEB-INF/login.jsp");
        reqDispatcher.include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String error = "";

        if (securityService.login(req)){
            resp.sendRedirect("/");
        }
        else{
            error = "username or password incorrect, please try again";

            req.setAttribute("error", error);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/login.jsp");
            requestDispatcher.include(req, resp);
        }

    }


    @Override
    public String getPattern() {
        return "/login";
    }
}
