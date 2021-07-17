//Note: Home Page -> Can't be accessed if user is not logged in.
//      Login Page -> Can only be access if user is login

package io.muic.ssc.webapp.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

public class HomeServlet extends AbstractRoutableHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        if (securityService.isAuthorize(req)){
            String username = securityService.getCurrentUsername(req);
            req.setAttribute("username", username);

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("WEB-INF/home.jsp");
            reqDispatcher.include(req, resp);
        }
        else{
            resp.sendRedirect("/login");
        }
    }

    @Override
    public String getPattern() {
        return "/index.jsp";
    }
}
