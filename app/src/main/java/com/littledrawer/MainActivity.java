package com.littledrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.net.response.BaseResponse;
import com.example.base.util.Log;
import com.littledrawer.http.model.User;
import com.littledrawer.http.service.UserService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitManager manager = RetrofitManager.getInstance();
        Map<String, String> map = new HashMap<>();
        map.put("username", "ldg000");
        map.put("password", "123456");
        manager.request(manager.getService(UserService.class)
                .test(map), new BaseListener<User>() {
            @Override
            public void onSuccess(User user) {
                ((TextView) findViewById(R.id.tv_show)).setText(user.toString());
            }

            @Override
            public void onFail(BaseException e) {
                ((TextView) findViewById(R.id.tv_show)).setText(e.msg);
            }
        });
    }
}
