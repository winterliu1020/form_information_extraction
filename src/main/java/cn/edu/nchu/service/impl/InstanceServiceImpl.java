package cn.edu.nchu.service.impl;

import cn.edu.nchu.entity.InstanceEntity;
import cn.edu.nchu.mappers.InstanceMapper;
import cn.edu.nchu.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuwentao on 2020-03-26 22:06
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    @Autowired
    InstanceMapper instanceMapper;

    @Override
    public int insertInstance(InstanceEntity instanceEntity) {
        return instanceMapper.insertInstance(instanceEntity);
    }

    @Override
    public int selectInstanceCountByModelID(int modelID) {
        return instanceMapper.selectInstanceCountByModelID(modelID);
    }

    @Override
    public int deleteInstanceByInstanceID(int instanceID) {
        return instanceMapper.deleteInstanceByInstanceID(instanceID);
    }

    @Override
    public String selectInstanceUrlByInstanceID(int instanceID) {
        return instanceMapper.selectInstanceUrlByInstanceID(instanceID);
    }
}
