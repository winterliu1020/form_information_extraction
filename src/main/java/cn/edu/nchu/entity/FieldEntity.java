package cn.edu.nchu.entity;

import lombok.Data;

/**
 * Created by liuwentao on 2020-03-24 22:55
 */
@Data
public class FieldEntity {
    private int fieldID;
    private int modelID;
    private String fieldName;
    private String fieldType;
    private int leftTopX;
    private int leftTopY;
    private int width;
    private int height;
}
