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
}
