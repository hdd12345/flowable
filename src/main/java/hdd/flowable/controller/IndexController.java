package hdd.flowable.controller;

import hdd.flowable.config.Const;
import hdd.flowable.util.Parametermap;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    IdentityService identityService;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request){
        Object obj = request.getSession().getAttribute(Const.SESSION_USER);
        if (obj!=null) {
            return "index";
        }
        return "login";
    }

    @RequestMapping(value="/login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        Parametermap parametermap = new Parametermap(request);
        Object username1 = parametermap.get("username");
        Object password1 = parametermap.get("password");
        System.out.println(username1+"##################"+password1);
        Map<String, String> map=new HashMap<>();
        User user = identityService.createUserQuery()
                .userId(username1.toString()).singleResult();
        if (user!=null) {
            String dbpassword = user.getPassword();
            if (password1!=null && password1.equals(dbpassword)) {
                map.put("status", "success");
                request.getSession().setAttribute(Const.SESSION_USER, user);
            }else {
                map.put("status", "fail");
            }
        }

        return map;
    }

    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Const.SESSION_USER);
        return "login";
    }
}
