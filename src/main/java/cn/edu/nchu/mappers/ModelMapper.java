package cn.edu.nchu.mappers;

import cn.edu.nchu.entity.ModelEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-15 09:49
 */

@Repository
public interface ModelMapper {
    List<ModelEntity> findModelByUserID(@Param("userID") int userID);
    int insertModel(ModelEntity modelEntity);
    int updateRecognizeCountByModelID(int modelID, int recognizeCount);
    int deleteModelByModelID(int modelID);
    List<ModelEntity> findDeleteModelByUserID(@Param("userID") int userID);

    int moveToDeleteModelArea(int modelID);

    int recoverModel(int modelID);

    int deleteAllModelInDeleteModelArea(int userID);

    String findModelUrlByModelID(int modelID);

    List<ModelEntity> findModelByUserIDAndQueryParam(int userID, String queryParam);

    ModelEntity findModelByModelID(int modelID);
}
