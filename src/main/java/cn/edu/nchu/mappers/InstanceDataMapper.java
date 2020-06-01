package cn.edu.nchu.mappers;

import cn.edu.nchu.entity.InstanceDataEntity;
import cn.edu.nchu.util.FieldUtil;
import cn.edu.nchu.util.TableBodyUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-30 21:04
 */
@Repository
public interface InstanceDataMapper {
    int insertInstanceData(InstanceDataEntity instanceDataEntity);

    List<TableBodyUtil> selectInstanceDataByModelID(int modelID);

    int deleteInstanceDataByInstanceID(int instanceID);

    int deleteInstanceDataByFieldID(int fieldID);

    List<FieldUtil> selectFieldUtilListByInstanceID(int instanceID);

    int updateInstanceDataByInstanceDataID(int instanceDataID, String instanceData);
}
