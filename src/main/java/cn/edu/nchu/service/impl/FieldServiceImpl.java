package cn.edu.nchu.service.impl;

import cn.edu.nchu.entity.FieldEntity;
import cn.edu.nchu.mappers.FieldMapper;
import cn.edu.nchu.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-24 23:17
 */
@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    FieldMapper fieldMapper;

    @Override
    public int insertField(FieldEntity fieldEntity) {
        return fieldMapper.insertField(fieldEntity);
    }

    @Override
    public List<FieldEntity> findFieldByModelID(int modelID) {
        return fieldMapper.findFieldByModelID(modelID);
    }

    @Override
    public int deleteFieldByFieldID(int fieldID) {
        return fieldMapper.deleteFieldByFieldID(fieldID);
    }

    @Override
    public int selectFieldCountByModelID(int modelID) {
        return fieldMapper.selectFieldCountByModelID(modelID);
    }

    @Override
    public FieldEntity getFieldEntityByFieldID(int fieldID) {
        return fieldMapper.getFieldEntityByFieldID(fieldID);
    }

    @Override
    public int updateFieldByFieldID(int fieldID, String fieldName, String fieldType) {
        return fieldMapper.updateFieldByFieldID(fieldID, fieldName, fieldType);
    }
}
