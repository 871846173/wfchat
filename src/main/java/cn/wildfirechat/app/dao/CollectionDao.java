package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionDao {
    int addCollection(Collection collection);

    int deleteCollection(Collection collection);

    List<Collection> getCollectionListWithUid(@Param(value = "uid") String uid, @Param(value = "begin") Integer begin,
                                              @Param(value = "size") Integer size);

//   String getCollectionListWithUid1(String uid);

}
