package cn.edu.nchu.entity;

import lombok.Data;

/**
 * Created by liuwentao on 2020-03-30 20:42
 */
@Data
public class InstanceDataEntity {
    private int instanceDataID;
    private int instanceID;
    private int fieldID;
    private String instanceFieldValue;
}
