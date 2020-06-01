package cn.edu.nchu.mappers;

import cn.edu.nchu.entity.FieldEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-24 23:10
 */
@Repository
public interface FieldMapper {
    int insertField(FieldEntity fieldEntity);
    int deleteFieldByFieldID(Integer fieldID);
    List<FieldEntity> findFieldByModelID(int modelID);

    int selectFieldCountByModelID(int modelID);

    FieldEntity getFieldEntityByFieldID(int fieldID);

    int updateFieldByFieldID(int fieldID, String fieldName, String fieldType);
}
