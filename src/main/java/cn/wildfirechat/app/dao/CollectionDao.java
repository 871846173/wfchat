package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionDao {
    int addCollection(Collection collection);
    int deleteCollection(Collection collection);
    List<Collection> getCollectionListWithUid(String uid);
//   String getCollectionListWithUid1(String uid);

}
