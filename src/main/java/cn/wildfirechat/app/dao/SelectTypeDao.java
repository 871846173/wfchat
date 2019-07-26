package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.SelectType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SelectTypeDao {

    int updateSelectType(SelectType selectType);

    int insertSelectType(SelectType selectType);

    SelectType selectType(String userId);

    SelectType selectCCode(String userId);
}
