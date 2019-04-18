package com.littledrawer.http;

import com.example.base.net.AuthUtil;
import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 土小贵
 * @date 2019/4/18 8:58
 */
public class Test {

    public static void main(String[] args) {
        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("username", "ldg000");
        map.put("password", "123456");
        manager.request(manager.getService(UserService.class)
                .login(map), new BaseListener<User>() {
            @Override
            public void onSuccess(User user) {
                AuthUtil authUtil = AuthUtil.getInstance();
                authUtil.setLogin(true);
                authUtil.setUserId(user.id);
                authUtil.setNickName(user.nickName);
                authUtil.setToken(user.token);
            }

            @Override
            public void onFail(BaseException e) {
            }
        });
    }

}
