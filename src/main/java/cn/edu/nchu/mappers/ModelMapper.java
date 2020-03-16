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
}
