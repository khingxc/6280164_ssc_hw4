package io.muic.ssc.webapp.service;

import io.muic.ssc.webapp.model.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SecurityService {

    private Map<String, String> userCredentials = new HashMap<String, String>(){{
       put("admin", "123456");
       put("khingc", "99999");
    }};

    private UserService userService;

    public boolean isAuthorize(HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("username");
        return (username != null && userCredentials.containsKey(username));
    }

    public boolean authenticate(String username, String password, HttpServletRequest req){
        String passwordInDB = userCredentials.get(username);
        boolean isMatched = StringUtils.equals(password, passwordInDB);
        if (isMatched){
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
        if (user != null && Objects.equals(user.getPassword(), password)) {
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
