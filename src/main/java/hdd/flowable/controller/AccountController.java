package hdd.flowable.controller;

import hdd.flowable.config.Const;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/hdd")
public class AccountController {
    @RequestMapping(value="/rest/account",method = RequestMethod.GET)
    public String account(HttpServletRequest request) {
        //禁用了Spring Security,创建流程等操作时拿不到User信息会报错
//        User user = new UserEntityImpl();
//        user.setId("admin");
//        SecurityUtils.assumeUser(user);
//        return "{\"id\":\"admin\",\"firstName\":\"Test\",\"lastName\":\"Administrator\",\"email\":\"admin@flowable.org\",\"fullName\":\"Test Administrator\",\"groups\":[],\"privileges\":[\"access-idm\",\"access-task\",\"access-modeler\",\"access-admin\"]}\n" +
//                "";
        User user1 = (User) request.getSession().getAttribute(Const.SESSION_USER);
        if(user1 != null){
            SecurityUtils.assumeUser(user1);
            return "{\"id\":\""+user1.getId()+"\",\"firstName\":\""+user1.getFirstName()+"\",\"lastName\":\""+user1.getLastName()+"\",\"email\":\""+user1.getEmail()+"\",\"fullName\":\""+user1.getFirstName()+user1.getLastName()+"\",\"groups\":[],\"privileges\":[\"access-idm\",\"access-task\",\"access-modeler\",\"access-admin\"]}\n";
        }
        User user = new UserEntityImpl();
        user.setId("admin");
        SecurityUtils.assumeUser(user);
        return "{\"id\":\"admin\",\"firstName\":\"Test\",\"lastName\":\"Administrator\",\"email\":\"admin@flowable.org\",\"fullName\":\"Test Administrator\",\"groups\":[],\"privileges\":[\"access-idm\",\"access-task\",\"access-modeler\",\"access-admin\"]}\n";
    }
}
