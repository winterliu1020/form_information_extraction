package cn.edu.nchu.service;

import cn.edu.nchu.entity.InstanceEntity;

/**
 * Created by liuwentao on 2020-03-26 22:05
 */
public interface InstanceService {

    int insertInstance(InstanceEntity instanceEntity);
    int selectInstanceCountByModelID(int modelID);
    int deleteInstanceByInstanceID(int instanceID);
    String selectInstanceUrlByInstanceID(int instanceID);
}
