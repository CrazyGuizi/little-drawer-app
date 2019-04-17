package com.example.base.net;

import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.net.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author 土小贵
 * @date 2019/4/17 22:02
 */
public class Test {

    public static void main(String[] args) {
        RetrofitManager manager = RetrofitManager.getInstance();
        UserService service = manager.getService(UserService.class);
        Map<String, String> map = new HashMap<>();
        map.put("username", "ldg000");
        map.put("password", "123456");
        Call<BaseResponse<User>> call = service.test(map);
        manager.request(call, new BaseListener<User>() {
            @Override
            public void onSuccess(User user) {
                System.out.println(user);
            }

            @Override
            public void onFail(BaseException e) {
                System.out.println(e.msg);
            }
        });
    }

    public interface UserService {

//    @POST("user/login")
//    Observable<BaseResponse<Object>> test(@Body Map<String, String> map);


        @POST("user/login")
        Call<BaseResponse<User>> test(@Body Map<String, String> map);
//    @GET("\"openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
//    Call<String> test();

    }

    public class User {
        private Integer id;
        private String  nickName;
        private String  username;
        private String  password;
        private String  iconUrl;
        private String  token;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", nickName='" + nickName + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
