package cn.edu.nchu.controller;

import cn.edu.nchu.entity.FieldEntity;
import cn.edu.nchu.entity.ModelEntity;
import cn.edu.nchu.service.FieldService;
import cn.edu.nchu.service.InstanceDataService;
import cn.edu.nchu.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuwentao on 2020-03-25 11:12
 */
@Controller
public class FieldController {
    @Autowired
    FieldService fieldService;

    @Autowired
    ModelService modelService;

    @Autowired
    InstanceDataService instanceDataService;

    @ResponseBody
    @RequestMapping("/addField")
    public String addField(String fieldName, String fieldType, String leftTopX, String leftTopY, String width, String height, String modelID) {
        FieldEntity fieldEntity = new FieldEntity();
        fieldEntity.setFieldName(fieldName);
        fieldEntity.setFieldType(fieldType);
        fieldEntity.setLeftTopX(Integer.valueOf(leftTopX));
        fieldEntity.setLeftTopY(Integer.valueOf(leftTopY));
        fieldEntity.setWidth(Integer.valueOf(width));
        fieldEntity.setHeight(Integer.valueOf(height));
        fieldEntity.setModelID(Integer.valueOf(modelID));
        ModelEntity modelEntity = modelService.findModelByModelID(Integer.valueOf(modelID));
        if (modelEntity.getRecognizeCount() != 0) {
            return "该模板已识别过数据，不能再添加区块！";
        }

        int res = fieldService.insertField(fieldEntity);
        if (res == 1) {
            return "添加区块成功";
        } else {
            return "添加区块失败";
        }
    }


    @ResponseBody
    @RequestMapping("/getFieldEntityByFieldID")
    public FieldEntity getFieldEntityByFieldID(@RequestParam("fieldID") String fieldID) {

        FieldEntity fieldEntity = fieldService.getFieldEntityByFieldID(Integer.valueOf(fieldID));
        if (fieldEntity != null) {
            return fieldEntity;
        } else {
            return new FieldEntity();
        }
    }

    @ResponseBody
    @RequestMapping("/deleteField/{fieldID}")
    public String deleteField(@PathVariable Integer fieldID, @RequestParam("modelID") String modelID) {
        // 如果该区块识别了数据，因为你要删除区块，因为 field_tab 中的 fieldID 与 instance_data_tab 中的 fieldID 外键关联，所以首先要把该区块识别的数据删除，再去删区块
        // 1. 删除利用该区块识别的数据
        int deleteInstanceDataByFieldIDResult = instanceDataService.deleteInstanceDataByFieldID(fieldID);
        if (deleteInstanceDataByFieldIDResult >= 0) {
            // 2. 删除该区块
            int res = fieldService.deleteFieldByFieldID(fieldID);
            if (res == 1) {
                return "删除区块成功";
            } else {
                return "删除区块失败";
            }
        } else {
            return "在删除利用该区块识别的数据时识别造成最终删除区块失败";
        }
    }

    // 编辑区块（只能修改一些区块的细节，比如fieldName、fieldType）
    @ResponseBody
    @RequestMapping("/editField")
    public String editField(@RequestParam("fieldID") String fieldID, @RequestParam("fieldName") String fieldName, @RequestParam("fieldType") String fieldType) {
        // 根据fieldID更新fieldName和fieldType
        int res = fieldService.updateFieldByFieldID(Integer.valueOf(fieldID), fieldName, fieldType);
        if (res == 1) {
            return "区块更新成功";
        } else {
            return "区块更新失败";
        }

    }
}
