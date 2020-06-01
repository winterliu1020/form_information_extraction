package cn.edu.nchu.service;

import cn.edu.nchu.entity.FieldEntity;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-24 23:14
 */
public interface FieldService {
    int insertField(FieldEntity fieldEntity);
    List<FieldEntity> findFieldByModelID(int modelID);
    int deleteFieldByFieldID(int fieldID);
    int selectFieldCountByModelID(int modelID);
    FieldEntity getFieldEntityByFieldID(int fieldID);
    int updateFieldByFieldID(int fieldID, String fieldName, String fieldType);
}
