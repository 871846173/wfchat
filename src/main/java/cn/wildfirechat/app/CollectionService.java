package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.Collection;

import java.util.List;

public interface CollectionService {

    RestResult addCollection(String uid,String mid);
    RestResult deleteCollection(String uid,String mid);
    List<Collection> getCollectionListWithUid(String uid);
//    int getcollectionCountByMid(String mid);
    String getCollectionListWithUid1(String uid);
}
