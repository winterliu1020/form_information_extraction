package cn.edu.nchu.service.impl;

import cn.edu.nchu.entity.ModelEntity;
import cn.edu.nchu.mappers.ModelMapper;
import cn.edu.nchu.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-15 09:59
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ModelEntity> findModelByUserID(int userID) {
        return modelMapper.findModelByUserID(userID);
    }

    @Override
    public int insertModel(ModelEntity modelEntity) {
        int res = modelMapper.insertModel(modelEntity);
        return res;
    }

    @Override
    public int updateRecognizeCountByModelID(int modelID, int recognizeCount) {
        return modelMapper.updateRecognizeCountByModelID(modelID, recognizeCount);
    }

    @Override
    public int deleteModelByModelID(int modelID) {
        return modelMapper.deleteModelByModelID(modelID);
    }

    @Override
    public List<ModelEntity> findDeleteModelByUserID(int userID) {
        return modelMapper.findDeleteModelByUserID(userID);
    }

    @Override
    public int moveToDeleteModelArea(int modelID) {
        return modelMapper.moveToDeleteModelArea(modelID);
    }

    @Override
    public int recoverModel(int modelID) {
        return modelMapper.recoverModel(modelID);
    }

    @Override
    public int deleteAllModelInDeleteModelArea(int userID) {
        return modelMapper.deleteAllModelInDeleteModelArea(userID);
    }

    @Override
    public String findModelUrlByModelID(int modelID) {
        return modelMapper.findModelUrlByModelID(modelID);
    }

    @Override
    public List<ModelEntity> findModelByUserIDAndQueryParam(int userID, String queryParam) {
        return modelMapper.findModelByUserIDAndQueryParam(userID, queryParam);
    }

    @Override
    public ModelEntity findModelByModelID(int modelID) {
        return modelMapper.findModelByModelID(modelID);
    }
}
