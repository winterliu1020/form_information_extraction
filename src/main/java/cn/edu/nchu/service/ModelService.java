package cn.edu.nchu.service;

import cn.edu.nchu.entity.ModelEntity;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-15 09:59
 */
public interface ModelService {

    List<ModelEntity> findModelByUserID(int userID);

    int insertModel(ModelEntity modelEntity);

    int updateRecognizeCountByModelID(int modelID, int recognizeCount);

    int deleteModelByModelID(int modelID);

    List<ModelEntity> findDeleteModelByUserID(int userID);

    int moveToDeleteModelArea(int modelID);

    int recoverModel(int modelID);

    int deleteAllModelInDeleteModelArea(int userID);

    String findModelUrlByModelID(int modelID);

    List<ModelEntity> findModelByUserIDAndQueryParam(int userID, String queryParam);

    ModelEntity findModelByModelID(int modelID);
}
