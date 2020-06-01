package cn.edu.nchu.mappers;

import cn.edu.nchu.entity.InstanceEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by liuwentao on 2020-03-26 22:04
 */
@Repository
public interface InstanceMapper {
    int insertInstance(InstanceEntity instanceEntity);
    int selectInstanceCountByModelID(int modelID);

    int deleteInstanceByInstanceID(int instanceID);

    String selectInstanceUrlByInstanceID(int instanceID);
}
