package cn.edu.nchu.util;

import lombok.Data;

/**
 * Created by liuwentao on 2020-04-13 09:27
 */
@Data
public class FieldUtil {
    private int instanceDataID;
    private int fieldID;
    private String fieldName;
    private String instanceFieldValue;
    private int leftTopX;
    private int leftTopY;
    private int width;
    private int height;
}
