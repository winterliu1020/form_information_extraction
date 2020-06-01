package cn.edu.nchu.controller;

import cn.edu.nchu.entity.FieldEntity;
import cn.edu.nchu.entity.InstanceDataEntity;
import cn.edu.nchu.entity.InstanceEntity;
import cn.edu.nchu.entity.ModelEntity;
import cn.edu.nchu.service.FieldService;
import cn.edu.nchu.service.InstanceDataService;
import cn.edu.nchu.service.InstanceService;
import cn.edu.nchu.service.ModelService;
import cn.edu.nchu.util.FileUtil;
import cn.edu.nchu.util.RecognizeInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuwentao on 2020-03-26 19:55
 */
@Controller
public class InstanceController {
    private String folder = "/Users/liuwentao/Code/upload/instance"; // 实例上传文件夹

    @Autowired
    InstanceService instanceService;

    @Autowired
    FieldService fieldService;

    @Autowired
    InstanceDataService instanceDataService;

    @Autowired
    ModelService modelService;

    // 开始识别
    @ResponseBody
    @RequestMapping("/startRecognize")
    public RecognizeInfo startRecognize(HttpServletRequest request, @RequestParam("modelIDInModel") int modelIDInModel, @RequestParam(value = "instanceFiles", required = false) MultipartFile[] files) throws Exception{
        System.out.println("hhhh" +modelIDInModel);
        String url = "http://localhost:8000/imageInformation/startRecognize";
        List<InstanceEntity> instanceEntityList = new LinkedList<>();

        int instanceID = 0;
        for (int i = 0; i < files.length; i++) {
            // 首先要判断files[i]是否符合识别要求;这里只是简单判断要识别的图片的大小(宽和高)如果和模板大小相差超过5px就判断为不符合识别要求
            BufferedImage sourceImg = ImageIO.read(files[i].getInputStream());
            System.out.println("待识别图片的宽:" +sourceImg.getWidth()); // 源图宽度
            System.out.println("待识别图片的高" + sourceImg.getHeight()); // 源图高度

            // 拿到模板的宽和高
            int modelID = modelIDInModel;
            ModelEntity modelEntity = modelService.findModelByModelID(modelID);

            System.out.println("模板图片的宽:" + modelEntity.getModelWidth()); // 源图宽度
            System.out.println("模板图片的高" + modelEntity.getModelHeight()); // 源图高度


            if (Math.abs(sourceImg.getWidth() - modelEntity.getModelWidth()) > 5 || Math.abs(sourceImg.getHeight() - modelEntity.getModelHeight()) > 5) {
                System.out.println(files[i].getOriginalFilename() + "不符合识别要求，直接放弃");
                continue;
            }

            String newFileName = FileUtil.upload(folder, files[i], request);
            if (newFileName != null) {
                // 将这些newFileName全都insert到instance_tab表中，并且要带上modelID
                InstanceEntity instanceEntity = new InstanceEntity();
                instanceEntity.setModelID(modelIDInModel);
                instanceEntity.setInstanceUrl(newFileName);
                instanceID = instanceService.insertInstance(instanceEntity);
                System.out.println("利用mybatis插入一条纸质文档实例返回的instanceID：" + instanceEntity.getInstanceID());
                instanceEntityList.add(instanceEntity);
                System.out.println("--" + newFileName + "上传成功" + instanceID);
            } else {
                System.out.println("--" + newFileName + "上传失败");
            }
        }

        // 拿到这个modelID对应的所有区块对象
        List<FieldEntity> fieldEntityList = fieldService.findFieldByModelID(modelIDInModel);

        // 然后就是springboot和Django之间的数据传递了
        // 首先得把map转成json
        for (int i = 0; i < instanceEntityList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("instanceUrl", instanceEntityList.get(i).getInstanceUrl());
            map.put("fieldEntityList", fieldEntityList);
            String jsonStr = JSON.toJSONString(map);

            System.out.println("javaToDjango:"+jsonStr);

            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("javaToDjango", jsonStr);

            String json = "";
            RestTemplate restTemplate = new RestTemplate();
            String temp = restTemplate.postForObject(url, paramMap, String.class);
            System.out.println("返回值："+temp);

            // 将得到的json字符串转成json对象
            JSONObject json_test = JSONObject.parseObject(temp);
            JSONArray list = json_test.getJSONArray("res");

            // 获取到当前纸质文档对应的instanceID
            instanceID = instanceEntityList.get(i).getInstanceID();

            for (int j = 0; j < list.size(); j++) {
                JSONObject field = JSONObject.parseObject(list.get(j).toString());
                String fieldValue = field.getString("fieldValue");
                String fieldID = field.getString("fieldID");
                System.out.println(fieldValue + " " + fieldID);

                // 接着把这两个数据插入到数据库
                InstanceDataEntity instanceDataEntity = new InstanceDataEntity();
                instanceDataEntity.setInstanceID(instanceID);
                instanceDataEntity.setFieldID(Integer.valueOf(fieldID));
                instanceDataEntity.setInstanceFieldValue(fieldValue);
                int insertResult = instanceDataService.insertInstanceData(instanceDataEntity);
                System.out.println("插入实例数据结果：" + insertResult);
            }
        }
        int recognizeCount = instanceService.selectInstanceCountByModelID(modelIDInModel);
        int updateRes = modelService.updateRecognizeCountByModelID(modelIDInModel, recognizeCount);
        RecognizeInfo recognizeInfo = new RecognizeInfo("识别结束，请注意可能存在因不符合识别要求而直接放弃的图片", modelIDInModel,recognizeCount);
        return recognizeInfo;
    }

    // 通过instanceID来删除实例信息、该实例所识别出来的所有数据
    @ResponseBody
    @RequestMapping("deleteInstanceByInstanceID")
    public String deleteInstanceByInstanceID(@RequestParam("instanceID") String instanceIDStr, @RequestParam("modelID") String modelIDInInput) {
        int instanceID = Integer.valueOf(instanceIDStr);
        int modelID = Integer.valueOf(modelIDInInput);
        int instanceDataDeleteResult = instanceDataService.deleteInstanceDataByInstanceID(instanceID);
        int instanceDeleteResult = instanceService.deleteInstanceByInstanceID(instanceID);

        // 删除实例还要更新model实体中recognizeCount属性
        int recognizeCount = instanceService.selectInstanceCountByModelID(modelID);
        int updateRes = modelService.updateRecognizeCountByModelID(modelID, recognizeCount);

        return "实例及其数据删除成功";
    }
}
