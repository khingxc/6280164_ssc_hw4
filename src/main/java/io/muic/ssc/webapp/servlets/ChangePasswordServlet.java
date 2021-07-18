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

public class ChangePasswordServlet extends AbstractRoutableHttpServlet{

    private SecurityService securityService;

    @Override
    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (securityService.isAuthorize(req)){
            String username = StringUtils.trim((String) req.getParameter("username"));
            UserService userService = UserService.getInstance();

            User user = userService.findByUsername(username);
            req.setAttribute("user", user);
            req.setAttribute("username", user.getUsername());


            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/password.jsp");
            reqDispatcher.include(req, resp);

            req.getSession().removeAttribute("hasError");
            req.getSession().removeAttribute("message");
        }
        else{
            req.removeAttribute("hasError");
            req.removeAttribute("message");
            resp.sendRedirect("/login");
        }
    }

    @Override
    public String getPattern() {
        return "/user/password";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (securityService.isAuthorize(req)){
            String username = StringUtils.trim((String) req.getParameter("username"));
            String password = (String) req.getParameter("password");
            String cpassword = (String) req.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            User user = userService.findByUsername(username);
            String errorMessage = null;
            //check username validity
            if (user == null){
                errorMessage = String.format("User %s does not exist.", username);

            }
            //check display name validity
            else if (StringUtils.isBlank(password)){
                errorMessage = "Password can't be blank.";
            }

            else if (!StringUtils.equals(password, cpassword)){
                errorMessage = "Confirmed password mismatches";
            }

            if (errorMessage != null){
                req.getSession().setAttribute("hasError", true);
                req.getSession().setAttribute("message", errorMessage);
            }
            else{
                try{
                    userService.changePassword(username, password);
                    req.getSession().setAttribute("hasError", false);
                    req.getSession().setAttribute("message", String.format("Password for user %s has been changed successfully.", username));
                    resp.sendRedirect("/");
                    return;
                }
                catch (Exception e){
                    req.getSession().setAttribute("hasError", true);
                    req.getSession().setAttribute("message", e.getMessage());
                }
            }
            req.setAttribute("username", username);

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/password.jsp");
            reqDispatcher.include(req, resp);

            req.getSession().removeAttribute("hasError");
            req.getSession().removeAttribute("message");
        }
        else{
            req.removeAttribute("hasError");
            req.removeAttribute("message");
            resp.sendRedirect("/login");
        }
    }
}
