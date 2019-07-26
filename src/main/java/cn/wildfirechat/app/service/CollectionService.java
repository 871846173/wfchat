package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;

public interface CollectionService {

    RestResult addCollection(String uid, String mid);

    RestResult deleteCollection(String uid, String mid);

    RestResult getCollectionListWithUid(String uid, Integer page, Integer size);

    //    int getcollectionCountByMid(String mid);

    String getCollectionListWithUid1(String uid);
}
