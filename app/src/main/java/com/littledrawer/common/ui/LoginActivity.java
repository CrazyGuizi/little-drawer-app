package com.littledrawer.common.ui;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import retrofit2.Call;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.net.RetrofitManager;
import com.example.base.net.exception.BaseException;
import com.example.base.net.listener.BaseListener;
import com.example.base.net.response.BaseResponse;
import com.example.base.util.Log;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.littledrawer.R;
import com.littledrawer.common.BaseActivity;
import com.littledrawer.event.MessageEvent;
import com.littledrawer.event.UserLoginMessage;
import com.littledrawer.http.bean.User;
import com.littledrawer.http.service.UserService;
import com.littledrawer.util.Const;
import com.littledrawer.util.SharedPreUtil;
import com.littledrawer.util.Util;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private boolean isLogin = true;

    @BindView(R.id.tl_nick_name)
    TextInputLayout mNickNameLayout;
    @BindView(R.id.et_nick_name)
    TextInputEditText mNickName;
    @BindView(R.id.et_username)
    TextInputEditText mUsername;
    @BindView(R.id.et_password)
    TextInputEditText mPassword;
    @BindView(R.id.tl_password_again)
    TextInputLayout mPasswordAgainLayout;
    @BindView(R.id.et_password_again)
    TextInputEditText mPasswordAgain;
    @BindView(R.id.bt_commit)
    MaterialButton mCommit;
    @BindView(R.id.tv_click_to)
    TextView mClickToText;
    @BindView(R.id.tv_change)
    TextView mChangeText;


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        changeView();
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mCommit.setOnClickListener(v -> {
            String nickName = mNickName.getText().toString();
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();
            String passwordAgain = mPasswordAgain.getText().toString();

            if (isLogin) {
                // 登录
                // 判断信息完整性
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(mActivity,
                            getString(R.string.complete_all_info),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 登录操作
                Util.login(username, password);
            } else {
                // 注册
                // 判断信息是否填写完整
                if (TextUtils.isEmpty(nickName) || TextUtils.isEmpty(username) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain)) {
                    Toast.makeText(mActivity,
                            getString(R.string.complete_all_info),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 判断密码是否一致
                if (!password.equals(passwordAgain)) {
                    Toast.makeText(mActivity,
                            getString(R.string.password_is_not_same),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 验证用户名是否存在，不存在则注册
                validateUsername(username);
            }
        });

        mChangeText.setOnClickListener(v -> {
            isLogin = !isLogin;
            changeView();
        });
    }

    private void validateUsername(String username) {
        RetrofitManager manager = RetrofitManager.getInstance();
        HashMap<String, String> validateParam = new HashMap<>();
        validateParam.put(UserService.USERNAME, username);
        manager.request(manager.getService(UserService.class)
                        .validateUsername(validateParam),
                new BaseListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        // 成功返回结果则说明在数据库中找到该用户了
                        Toast.makeText(mActivity,
                                getString(R.string.username_is_exit), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(BaseException e) {
                        register();
                    }
                });
    }

    /**
     * 注册
     */
    private void register() {
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String nickName = mNickName.getText().toString();
        RetrofitManager manager = RetrofitManager.getInstance();
        HashMap<String, String> param = new HashMap<>();
        param.put(UserService.USERNAME, username);
        param.put(UserService.PASSWORD, password);
        param.put(UserService.NICK_NAME, nickName);
        manager.request(manager.getService(UserService.class)
                        .register(param),
                new BaseListener<User>() {
                    @Override
                    public void onSuccess(User user) {
                        if (user != null) {
                            // 注册成功后直接登录
                            Util.login(user.username, user.password);
                        }
                    }

                    @Override
                    public void onFail(BaseException e) {
                        Toast.makeText(mActivity,
                                getString(R.string.register_fail) + "," + e.msg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 切换登录注册
     */
    private void changeView() {
        if (isLogin) {
            mCommit.setText(getString(R.string.login));
            mClickToText.setText(getString(R.string.click_to_register));
            mClickToText.setVisibility(View.VISIBLE);
            mChangeText.setText(getString(R.string.to_register));
            mPasswordAgainLayout.setVisibility(View.GONE);
            mNickNameLayout.setVisibility(View.GONE);
        } else {
            mCommit.setText(getString(R.string.register));
            mClickToText.setVisibility(View.GONE);
            mChangeText.setText(getString(R.string.to_login));
            mPasswordAgainLayout.setVisibility(View.VISIBLE);
            mNickNameLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event instanceof UserLoginMessage) {
            UserLoginMessage userLoginMessage = (UserLoginMessage) event;
            if (userLoginMessage.isLogin) {
                if (userLoginMessage.user != null) {
                    finish();
                } else {
                    Toast.makeText(mActivity, userLoginMessage.msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
