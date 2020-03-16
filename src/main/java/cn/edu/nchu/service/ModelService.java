package cn.edu.nchu.service;

import cn.edu.nchu.entity.ModelEntity;

import java.util.List;

/**
 * Created by liuwentao on 2020-03-15 09:59
 */
public interface ModelService {

    List<ModelEntity> findModelByUserID(int userID);
}
