package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends AbstractRoutableHttpServlet{

    private SecurityService securityService;

    @Override
    public void setSecurityService(SecurityService securityService){
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (securityService.isAuthorize(req)){
//            String username = securityService.getCurrentUsername(req);
//            UserService userService = UserService.getInstance();


//            req.setAttribute("user", userService.findByUsername(username));

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/create.jsp");
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
        return "/user/create";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (securityService.isAuthorize(req)){
            String username = StringUtils.trim((String) req.getParameter("username"));
            String displayName = StringUtils.trim((String) req.getParameter("displayName"));
            String password = (String) req.getParameter("password");
            String cpassword = (String) req.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            String errorMessage = null;
            //check username validity
            if (userService.findByUsername(username) != null){
                errorMessage = String.format("Username %s has already been used.", username);

            }
            //check display name validity
            else if (StringUtils.isBlank(displayName)){
                errorMessage = "Display Name can't be blank.";
            }
            //check password validity
            else if (StringUtils.isBlank(password)){
                errorMessage = "Password can't be blank.";
            }
            //check confirm password is correct
            else if (!StringUtils.equals(password, cpassword)){
                errorMessage = "Confirmed password mismatches.";
            }

            if (errorMessage != null){
                req.getSession().setAttribute("hasError", true);
                req.getSession().setAttribute("message", errorMessage);
            }
            else{
                try{
                    userService.createUser(username, password, displayName);
                    req.getSession().setAttribute("hasError", false);
                    req.getSession().setAttribute("message", String.format("Username %s has been created successfully.", username));
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
            req.setAttribute("password", password);
            req.setAttribute("cpassword", cpassword);

//            String username = securityService.getCurrentUsername(req);
//            UserService userService = UserService.getInstance();


//            req.setAttribute("user", userService.findByUsername(username));

            RequestDispatcher reqDispatcher = req.getRequestDispatcher("/WEB-INF/create.jsp");
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
