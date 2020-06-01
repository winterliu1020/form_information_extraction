package cn.edu.nchu.entity;

import lombok.Data;

import java.util.Date;


/**
 * Created by liuwentao on 2020-03-15 09:40
 */

@Data
public class ModelEntity {
    private int modelID;
    private String modelName;
    private int modelHeight;
    private int modelWidth;
    private int userID;
    private String modelUrl;
    private Date modelCreateTime;
    private int recognizeCount;
    private int deleteMark; // 0 未被删除； 1 已经删除（在回收站中）;  2 彻底删除
}
