package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.FriendDao;
import cn.wildfirechat.app.pojo.Friend;
import cn.wildfirechat.app.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Override
    public RestResult deleteFriend(String userId, String friendUid, Integer state) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(friendUid)) {
            return RestResult.error(RestResult.RestCode.ERROR_PARAMATER);
        }
        Friend friend = new Friend();
        friend.setUserId(userId);
        friend.setFriendUid(friendUid);
        friend.setState(state);
        int status = friendDao.deleteFriend(friend);

        friend.setUserId(friendUid);
        friend.setFriendUid(userId);
        int status1 = friendDao.deleteFriend(friend);
        if (status == 1 && status1 == 1) {
            return RestResult.ok("删除好友成功");
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);

    }
}
