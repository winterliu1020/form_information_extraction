package cn.edu.nchu.controller;

import cn.edu.nchu.entity.UserEntity;
import cn.edu.nchu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpSession;

/**
 * Created by liuwentao on 2020-03-14 11:06
 */
@Controller
public class LoginController implements WebMvcConfigurer{

    @Autowired
    private UserService userService;

    /**
     * @name: addViewControllers
     * @description: 页面控制，这个函数可以不用每个请求都写一个controller，只需要加一行registry.addViewController就行了
     * @param registry
     * @return: void
     * @date: 2020-03-18 16:12
     * @auther: liuwentao
     *
    */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("sign-in");
        registry.addViewController("/toRegister").setViewName("sign-up");
    }


    /**
     * @name: login
     * @description: 登录请求
     * @param username
     * @param password
     * @param session
     * @param model
     * @return: java.lang.String
     * @date: 2020-03-18 16:14
     * @auther: liuwentao
     *
    */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributesModelMap model) {
        UserEntity userEntity = userService.findUserByUserNamePassWord(username, password);
        System.out.println("------");
        if (userEntity == null) {
            model.addAttribute("message", "用户不存在或者账号密码错误！");
            return "sign-in";
        } else {
            session.setAttribute("user", userEntity);
            return "redirect:/listModel";
        }

    }

}
