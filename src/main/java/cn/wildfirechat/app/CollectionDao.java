package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CollectionDao {
    int addCollection(Collection collection);
    int deleteCollection(Collection collection);
}
