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

public class EditUserServlet extends AbstractRoutableHttpServlet{

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
            req.setAttribute("displayName", user.getDisplayName());

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/edit.jsp");
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
        return "/user/edit";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (securityService.isAuthorize(req)){
            String username = StringUtils.trim((String) req.getParameter("username"));
            String displayName = StringUtils.trim((String) req.getParameter("displayName"));

            UserService userService = UserService.getInstance();
            User user = userService.findByUsername(username);
            String errorMessage = null;
            //check username validity
            if (user == null){
                errorMessage = String.format("User %s does not exist.", username);

            }
            //check display name validity
            else if (StringUtils.isBlank(displayName)){
                errorMessage = "Display Name can't be blank.";
            }

            if (errorMessage != null){
                req.getSession().setAttribute("hasError", true);
                req.getSession().setAttribute("message", errorMessage);
            }
            else{
                try{
                    userService.updateUserByUsername(username, displayName);
                    req.getSession().setAttribute("hasError", false);
                    req.getSession().setAttribute("message", String.format("Username %s has been updated successfully.", username));
                    resp.sendRedirect("/");
                    return;
                }
                catch (Exception e){
                    req.getSession().setAttribute("hasError", true);
                    req.getSession().setAttribute("message", e.getMessage());
                }
            }
            req.setAttribute("username", username);
            req.setAttribute("displayName", displayName);

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/edit.jsp");
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
