package io.muic.ssc.webapp.service;

import io.muic.ssc.webapp.model.User;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SecurityService {

    private UserService userService;

    public boolean isAuthorize(HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("username");
        // do checking
        // get user from user database
        return (username != null && userService.findByUsername(username) != null);
    }

    public boolean authenticate(String username, String password, HttpServletRequest req){
        User user = userService.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())){
            req.getSession().setAttribute("username", username);
            return true;
        }
        else{
            return false;
        }
    }

    public String getCurrentUsername(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object usernameObject = session.getAttribute("username");
        return (String) usernameObject;
    }

    public boolean login(HttpServletRequest req){
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }

    public void logout(HttpServletRequest req){
        req.getSession().invalidate();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
