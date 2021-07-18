//Note: Home Page -> Can't be accessed if user is not logged in.
//      Login Page -> Can only be access if user is login

package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.model.User;
import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends AbstractRoutableHttpServlet{

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

        try {
            User currentUser = userService.findByUsername(username);
            User deletingUser = userService.findByUsername(req.getParameter("username"));
            if (StringUtils.equals(currentUser.getUsername(), deletingUser.getUsername())){
                req.getSession().setAttribute("hasError", true);
                req.getSession().setAttribute("message", "Unable to delete your own account");
            }
            else{
                if (userService.deleteUserByUsername(deletingUser.getUsername())){
                    req.getSession().setAttribute("hasError", false);
                    req.getSession().setAttribute("message", String.format("User %s is successfully deleted.", deletingUser.getUsername()));
                }
                else{
                    req.getSession().setAttribute("hasError", true);
                    req.getSession().setAttribute("message", String.format("Unable to delete the account named %s", deletingUser.getUsername()));
                }
            }
        }
        catch(Exception e){
            req.getSession().setAttribute("hasError", true);
            req.getSession().setAttribute("message", String.format("Unable to delete the account named %s", req.getParameter("username")));
        }
        resp.sendRedirect("/");
        }
        else{
            resp.sendRedirect("/login");
        }
    }

    @Override
    public String getPattern() {
        return "/user/delete";
    }

}
