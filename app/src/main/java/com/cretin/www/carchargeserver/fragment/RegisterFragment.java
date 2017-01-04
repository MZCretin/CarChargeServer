package com.cretin.www.carchargeserver.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cretin.www.carchargeserver.BaseApplication;
import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BaseFragment;
import com.cretin.www.carchargeserver.utils.UiUtils;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment {
    public static final String TAG = "RegisterFragment";
    @Bind(R.id.iv_phone)
    ImageView ivPhone;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.iv_password)
    ImageView ivPassword;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.rl_money)
    RelativeLayout rlMoney;
    @Bind(R.id.iv_code)
    ImageView ivCode;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.bt_submit)
    Button btSubmit;
    @Bind(R.id.tv_protocol)
    TextView tvProtocol;
    private Handler handlerCode = BaseApplication.getHandler();
    private int countDown = 60;// 倒计时秒数
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDown != 1) {
                countDown--;
                tvCode.setText("还剩" + countDown + "S");
                tvCode.setEnabled(false);
                handlerCode.postDelayed(this, 1000);
            } else if (countDown == 1) {
                countDown = 60;
                tvCode.setEnabled(true);
                tvCode.setText("重新发送");
            } else if (countDown <= 0) {
                countDown = 60;
                tvCode.setEnabled(true);
                tvCode.setText("获取验证码");
            }
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View contentView, Bundle savedInstanceState) {
        hidTitleView();
        hidProgressView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick({R.id.tv_code, R.id.bt_submit, R.id.tv_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getCode();
                break;
            case R.id.bt_submit:
                register();
                break;
            case R.id.tv_protocol:
                break;
        }
    }

    //获取验证码
    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            UiUtils.showToastInAnyThread("手机号不能为空!");
            return;
        }
    }

    //注册操作
    private void register() {
        final String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            UiUtils.showToastInAnyThread("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            UiUtils.showToastInAnyThread("密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            UiUtils.showToastInAnyThread("验证码不能为空");
            return;
        }
    }
}
