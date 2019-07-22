package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.SelectType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SelectTypeDao {

    int updateSelectType(SelectType selectType);

    int insertSelectType(SelectType selectType);
}
