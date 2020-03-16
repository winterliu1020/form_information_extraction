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

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/sign-in");
        registry.addViewController("/toRegister").setViewName("sign-up");
    }



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
