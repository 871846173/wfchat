package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.CollectionDao;
import cn.wildfirechat.app.pojo.Collection;
import cn.wildfirechat.app.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Component
@org.springframework.stereotype.Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;

    @Override
    public RestResult addCollection(String uid, String mid) {
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(mid)) {
            return RestResult.error(RestResult.RestCode.ERROR_PARAMATER);
        }
        Collection collection = new Collection();
        collection.setMid(mid);
        collection.setUid(uid);
        int status = collectionDao.addCollection(collection);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_ADD_COLLECTION);
        }

    }

    @Override
    public RestResult deleteCollection(String uid, String mid) {
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(mid)) {
            return RestResult.error(RestResult.RestCode.ERROR_PARAMATER);
        }
        Collection collection = new Collection();
        collection.setMid(mid);
        collection.setUid(uid);
        int status = collectionDao.deleteCollection(collection);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_DELETE_COLLECTION);
        }

    }

    @Override
    public RestResult getCollectionListWithUid(String uid, Integer page, Integer size) {

        //从第几条开始查询
        Integer begin = (page - 1) * size;
        List<Collection> list = collectionDao.getCollectionListWithUid(uid, begin, size);
        if (list == null) {
            return RestResult.error(RestResult.RestCode.ERROR_PARAMATER);
        }
        if (list.size() == 0) {
            return RestResult.error(RestResult.RestCode.ERROR_NO_COLLECTION);
        }
//        String resultList= JSON.toJSONString(list);
//        CollectionList result=new CollectionList();
//        result.setList(list);
        return RestResult.ok(list);
//        return  null;
    }

    @Override
    public String getCollectionListWithUid1(String uid) {
//        String a = collectionDao.getCollectionListWithUid1(uid);
        return null;
    }
}
