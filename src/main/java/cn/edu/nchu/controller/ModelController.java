package cn.edu.nchu.controller;

import cn.edu.nchu.entity.ModelEntity;
import cn.edu.nchu.entity.UserEntity;
import cn.edu.nchu.service.ModelService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liuwentao on 2020-03-15 09:46
 */
@Controller
public class ModelController {

    @Autowired
    private ModelService modelService;

    @RequestMapping("/listModel")
    public String listModel(HttpSession session, Model model) {
        System.out.println("/listMode");
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if (userEntity == null) {
            System.out.println("session拿到的为空");
        } else {
            System.out.println("session拿到的不为空");
            System.out.println(userEntity.toString());
            List<ModelEntity> modelEntities = modelService.findModelByUserID(userEntity.getUserID());
            model.addAttribute("modelList",modelEntities);
        }

        /* 测试 PageHelper
        PageHelper.startPage(1,2);
        System.out.println(modelService.findModelByUserID(1));
         */

        return "home";
    }
}
