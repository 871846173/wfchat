package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;

public interface FriendService {

    RestResult deleteFriend(String userId, String friendUid, Integer state);
}
