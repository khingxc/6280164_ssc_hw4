//Note: Home Page -> Can't be accessed if user is not logged in.
//      Login Page -> Can only be access if user is login

package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.model.User;
import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Date;

public class HomeServlet extends AbstractRoutableHttpServlet{

    private SecurityService securityService;

    @Override
    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        if (securityService.isAuthorize(req)){
            String username = securityService.getCurrentUsername(req);
            UserService userService = UserService.getInstance();

            req.setAttribute("currentUser", userService.findByUsername(username));
            req.setAttribute("users", userService.findAll());

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("WEB-INF/home.jsp");
            reqDispatcher.include(req, resp);

            req.removeAttribute("hasError");
            req.removeAttribute("message");
        }
        else{
            req.removeAttribute("hasError");
            req.removeAttribute("message");
            resp.sendRedirect("/login");
        }
    }

    @Override
    public String getPattern() {
        return "/index.jsp";
    }

}
