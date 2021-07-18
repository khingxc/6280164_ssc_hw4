package io.muic.ssc.webapp.servlets;

import io.muic.ssc.webapp.service.SecurityService;
import io.muic.ssc.webapp.service.UserService;

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

//        if ((Boolean) req.getSession().getAttribute("flashSessionRead")){
//            //flag the flash session to be removed next time
//            req.removeAttribute("hasError");
//            req.removeAttribute("message");
//        }
//        else{
//            req.removeAttribute("hasError");
//        }

        if (securityService.isAuthorize(req)){
            String username = securityService.getCurrentUsername(req);
            UserService userService = UserService.getInstance();


            req.setAttribute("user", userService.findByUsername(username));

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
}
