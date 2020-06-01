package cn.edu.nchu.controller;

import cn.edu.nchu.entity.FieldEntity;
import cn.edu.nchu.entity.ModelEntity;
import cn.edu.nchu.entity.UserEntity;
import cn.edu.nchu.service.FieldService;
import cn.edu.nchu.service.ModelService;
import cn.edu.nchu.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Created by liuwentao on 2020-03-15 09:46
 */
@Controller
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private FieldService fieldService;

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
            model.addAttribute("models",modelEntities);
        }

        return "home";
    }

    @RequestMapping("/listModelWithQueryParam")
    public String listModelWithQueryParam(HttpSession session, Model model, @RequestParam("queryParam") String queryParam) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if (userEntity == null) {
            System.out.println("session拿到的为空");
        } else {
            System.out.println("session拿到的不为空");
            System.out.println(userEntity.toString());
            List<ModelEntity> modelEntities = modelService.findModelByUserIDAndQueryParam(userEntity.getUserID(), queryParam);

            model.addAttribute("models",modelEntities);
        }
        return "home";
    }

    // 进入模板编辑界面
    @RequestMapping("/editModel/{modelID}")
    public String editModel(HttpServletRequest request, @PathVariable Integer modelID, Model model) {
        System.out.println(modelID);
        List<FieldEntity> fieldEntities = fieldService.findFieldByModelID(modelID);
        // 根据modelID找到modelUrl,然后传到editModel界面，放到img的src中
        String modelUrl = modelService.findModelUrlByModelID(modelID);
        System.out.println("该模板的URL：" + modelUrl);
        model.addAttribute("modelUrl",modelUrl);
        model.addAttribute("fields", fieldEntities);
        model.addAttribute("modelID", modelID);
        return "editModel";
    }

    @RequestMapping("/createModel")
    public String createModel(HttpServletRequest request,
                              @RequestParam("inputFile") MultipartFile file,
                              @RequestParam("modelName") String modelName,
                              @RequestParam("userID") String userID,
                              Model model) throws Exception {
        String folder = "/Users/liuwentao/Code/upload/model/";
        BufferedImage image = ImageIO.read(file.getInputStream());
        int height = image.getHeight(); // 上传的模板的高度
        int width = image.getWidth(); // 上传的模板的宽度
        String newFileName = FileUtil.upload(folder, file, request); // 将模板图片存储到folder，并得到能够获取该文件的一个新的文件名(加了时间戳)
        if (newFileName != null) {
//            String fileURL = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/")) + "/upload/model/" + newFileName;
            System.out.println("文件上传成功，" + userID + "创建了模板： " + modelName + "   " + newFileName);

            // 然后要将谁创建了模板这条数据保存到数据库
            ModelEntity modelEntity = new ModelEntity();
            modelEntity.setModelName(modelName);
            modelEntity.setUserID(Integer.valueOf(userID));
            modelEntity.setModelUrl(newFileName);
            modelEntity.setModelHeight(height);
            modelEntity.setModelWidth(width);
            modelEntity.setModelCreateTime(new Date());
            modelEntity.setRecognizeCount(0);
            modelEntity.setDeleteMark(0);
            int res = modelService.insertModel(modelEntity);
            if (res == 1) {
                return "redirect:/listModel";
            } else {
                System.out.println("创建模板失败");
            }

            /** 以下为之前考虑的方案，让用户上传word，然后word转成图片，多张图片进行合并。但是和老师讨论了一下需求，老师说只要用户上传模板的图片就行了，其实这张图片也是来自高拍仪扫描得到的 **/
            /**
            // 文件上传成功之后需要再将上传成功的Word文件转成图片格式，这里调用别人的API来转换
            String tmp = userID;
            // 将转换成的图片放到dir这个文件夹，文件夹不存在就新建一个
            File dir = new File("/Users/liuwentao/Code/upload/" + tmp);
            if (!dir.exists()) {
                dir.mkdir();
            }
            Config.setDefaultSecret("C0jwBX79SSWIgynR");
            ConvertApi.convert("docx", "jpg",
                    new Param("File", Paths.get(folder + newFileName)),
                    new Param("FileName", "haha"),
                    new Param("ImageHeight", "1500"),
                    new Param("ImageWidth", "1000")

            ).get().saveFilesSync(Paths.get("/Users/liuwentao/Code/upload/" + tmp));

//            // 再将dir这个文件夹中所有图片合成一张图片
//            List<File> files = new ArrayList<>();
//            File[] fileList = dir.listFiles(); // 拿到dir目录下所有文件
//            System.out.println("文件个数：" + fileList.length);
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].exists()) {
//                    System.out.println("文件：" + fileList[i].getName());
//                    files.add(fileList[i]);
//                }
//            }
//            String newPicName = new Date().getTime() +".jpg"; // 合成后的图片名称
//            PicUtil.jointPic(files, "/Users/liuwentao/Code/upload/pic/" + newPicName); // 将合成后的图片写入到这个文件夹中
            // 再将之前的文件夹tmp中所有文件都删除
//            for (int i = 0; i < fileList.length; i++) {
//                if (fileList[i].exists()) {
//                    fileList[i].delete();
//                }
//            }
             **/


        } else {
            System.out.println("文件上传失败");
            return "redirect:/listModel";
        }
        return "redirect:/listModel";
    }

    // 根据modelID彻底删除模板
    @ResponseBody
    @RequestMapping("/deleteModelByModelID")
    public String deleteModelByModelID(@RequestParam("modelID") String modelIDStr) {
        int modelID = Integer.valueOf(modelIDStr);

        int deleteModelResult = modelService.deleteModelByModelID(modelID);
        if (deleteModelResult == 1) {
            return "删除模板成功";
        }
        return "删除模板失败";
    }


    // 进入回收站（可以看到该用户删除的所有模板）
    @RequestMapping("/showDeleteModelArea")
    public String deleteModelArea(HttpSession session, Model model) {
        return "showDeleteModelx";
    }

    // 获取被移动到回收站中的模板的数据
    @ResponseBody
    @RequestMapping("/loadDeleteModelData")
    public String loadDeleteModelData(HttpSession session) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        List<ModelEntity> modelEntities = null;
        if (userEntity == null) {
            System.out.println("session拿到的为空");
        } else {
            System.out.println("session拿到的不为空");
            modelEntities = modelService.findDeleteModelByUserID(userEntity.getUserID());

        }
        String json= JSON.toJSONString(modelEntities);

        return json;
    }


    @ResponseBody
    @RequestMapping("/moveToDeleteModelArea")
    public String moveToDeleteModelArea(@RequestParam("modelID") String modelIDStr) {
        int modelID = Integer.valueOf(modelIDStr);
        int moveResult = modelService.moveToDeleteModelArea(modelID);

        return "已经移动到回收站";
    }

    // 恢复回收站中的model
    @ResponseBody
    @RequestMapping("/recoverModel")
    public String recoverModel(@RequestParam("modelID") String modelIDStr) {
        if (modelIDStr == null) {
            return "modelID";
        }
        int modelID = Integer.valueOf(modelIDStr);
        int recoverModelResult = modelService.recoverModel(modelID);

        return "模板已恢复";
    }

    // 清空回收站
    @ResponseBody
    @RequestMapping("/deleteAllModelInDeleteModelArea")
    public String deleteAllModelInDeleteModelArea(HttpSession session) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if (userEntity == null) {
            System.out.println("session拿到的为空");
        } else {
            System.out.println("session拿到的不为空");
            System.out.println(userEntity.toString());
            int deleteAllModelInDeleteModelAreaResult = modelService.deleteAllModelInDeleteModelArea(userEntity.getUserID());
        }
        return "回收站已清空";
    }

}
