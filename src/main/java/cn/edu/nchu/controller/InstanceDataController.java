package cn.edu.nchu.controller;

import cn.edu.nchu.entity.FieldEntity;
import cn.edu.nchu.service.FieldService;
import cn.edu.nchu.service.InstanceDataService;
import cn.edu.nchu.service.InstanceService;
import cn.edu.nchu.util.FieldUtil;
import cn.edu.nchu.util.TableBodyUtil;
import cn.edu.nchu.util.TableHeadUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwentao on 2020-03-30 20:40
 */
@Controller
public class InstanceDataController {

    @Autowired
    FieldService fieldService;

    @Autowired
    InstanceDataService instanceDataService;

    @Autowired
    InstanceService instanceService;

    @RequestMapping("/insertInstanceData")
    public void insertInstanceData() {

    }


    @RequestMapping("/showInstanceData/{modelID}")
    public String selectInstanceData(@PathVariable("modelID") int modelID, Model model) {
        // 这里要根据modelID找到通过该模板识别的所有instanceFieldValue,然后重组成showEntity，并返回一个list<showEntity>
        System.out.println("查看模板" + modelID + "的所有识别数据");

        model.addAttribute("modelID", modelID);
        return "showInstanceData";
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Map loadData(int modelID) {
        // 首先它的找到modelID对应的有几个区块
        List<FieldEntity> list = fieldService.findFieldByModelID(modelID);
        List<TableHeadUtil> tableHeadUtilList = new ArrayList<>();

        TableHeadUtil instanceIDTableHead = new TableHeadUtil();
        instanceIDTableHead.setField("instanceID");
        instanceIDTableHead.setTitle("instanceID");
        tableHeadUtilList.add(instanceIDTableHead);
        // 然后用区块list重组成json
        for (int i = 0; i < list.size(); i++) {
            TableHeadUtil tableHeadUtil = new TableHeadUtil();
            tableHeadUtil.setField(list.get(i).getFieldID() + "");
            tableHeadUtil.setTitle(list.get(i).getFieldName());
            tableHeadUtilList.add(tableHeadUtil);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("columns", tableHeadUtilList);

        // 根据modelID去数据库中查询出该模板识别的所有数据然后填充到TableBodyUtil工具类list中
        List<TableBodyUtil> tableBodyUtilList = new ArrayList<>();
        tableBodyUtilList = instanceDataService.selectInstanceDataByModelID(modelID);
        System.out.println("--- " + tableBodyUtilList.toString());

        // 首先要找到这个modelID有几个区块
        int fieldCount = fieldService.selectFieldCountByModelID(modelID);

        // 因为我在数据库中根据instanceID排序了，所以利用里层for循环构造map,一个map就是一个实例的数据
        List<Map<String, String>> mylist = new ArrayList<>();
        for (int i = 0; i < tableBodyUtilList.size();) {
            Map<String, String> map1 = new HashMap<>();
            map1.put("instanceID", tableBodyUtilList.get(i).getInstanceID() + "");
            for (int j = 0; j < fieldCount; j++) {
                map1.put(tableBodyUtilList.get(i).getFieldID() + "", tableBodyUtilList.get(i).getInstanceFieldValue());
                i++;
            }
            mylist.add(map1);
        }

        map.put("data",mylist);
        String json= JSON.toJSONString(map);
        System.out.println(json);

        return map;
    }


    @RequestMapping("/editInstanceData/{instanceID}")
    public String editInstanceData(@PathVariable("instanceID") int instanceID, Model model) {
        List<FieldUtil> fieldUtilList = new ArrayList<>();
        // 根据instanceID去进行instance_tab和instance_data_tab和field_tab三表查询，查询结果为fieldUtilList
        fieldUtilList = instanceDataService.selectFieldUtilListByInstanceID(instanceID);
        String instanceUrl = instanceService.selectInstanceUrlByInstanceID(instanceID);
        System.out.println("lllllllllll----------" + fieldUtilList.toString());

        model.addAttribute("fieldUtilList", fieldUtilList);
        model.addAttribute("instanceID", instanceID);
        model.addAttribute("instanceUrl", instanceUrl);
        return "editInstanceData";
    }


    @ResponseBody
    @RequestMapping("/updateInstanceDataByInstanceDataID")
    public String updateInstanceDataByInstanceDataID(@RequestParam("instanceDataID") String instanceDataID, @RequestParam("instanceData") String instanceData) {
        System.out.println("将" + instanceDataID + "的值更新为：" + instanceData);
        int res = instanceDataService.updateInstanceDataByInstanceDataID(Integer.valueOf(instanceDataID), instanceData);
        if (res == 1) {
            return "更新成功";
        }
        return "更新失败";
    }
}
