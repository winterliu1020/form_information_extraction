package cn.edu.nchu.service.impl;

import cn.edu.nchu.entity.InstanceDataEntity;
import cn.edu.nchu.mappers.InstanceDataMapper;
import cn.edu.nchu.service.InstanceDataService;
import cn.edu.nchu.util.FieldUtil;
import cn.edu.nchu.util.TableBodyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-30 21:01
 */
@Service
public class InstanceDataServiceImpl implements InstanceDataService {
    @Autowired
    InstanceDataMapper instanceDataMapper;

    @Override
    public int insertInstanceData(InstanceDataEntity instanceDataEntity) {
        return instanceDataMapper.insertInstanceData(instanceDataEntity);
    }

    @Override
    public List<TableBodyUtil> selectInstanceDataByModelID(int modelID) {
        return instanceDataMapper.selectInstanceDataByModelID(modelID);
    }

    @Override
    public int deleteInstanceDataByInstanceID(int instanceID) {
        return instanceDataMapper.deleteInstanceDataByInstanceID(instanceID);
    }

    @Override
    public int deleteInstanceDataByFieldID(int fieldID) {
        return instanceDataMapper.deleteInstanceDataByFieldID(fieldID);
    }

    @Override
    public List<FieldUtil> selectFieldUtilListByInstanceID(int instanceID) {
        return instanceDataMapper.selectFieldUtilListByInstanceID(instanceID);
    }

    @Override
    public int updateInstanceDataByInstanceDataID(int instanceDataID, String instanceData) {
        return instanceDataMapper.updateInstanceDataByInstanceDataID(instanceDataID, instanceData);
    }
}
