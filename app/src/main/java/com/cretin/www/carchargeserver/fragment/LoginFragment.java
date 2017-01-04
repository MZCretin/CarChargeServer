package com.cretin.www.carchargeserver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BaseFragment;
import com.cretin.www.carchargeserver.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    public static final String TAG = "LoginFragment";
    @Bind( R.id.iv_phone )
    ImageView ivPhone;
    @Bind( R.id.et_phone )
    EditText etPhone;
    @Bind( R.id.iv_password )
    ImageView ivPassword;
    @Bind( R.id.et_password )
    EditText etPassword;
    @Bind( R.id.rl_money )
    RelativeLayout rlMoney;
    @Bind( R.id.bt_submit )
    Button btSubmit;
    @Bind( R.id.tv_register )
    TextView tvRegister;
    @Bind( R.id.tv_forget_psw )
    TextView tvForgetPsw;

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
        return R.layout.fragment_login;
    }

    @OnClick( {R.id.bt_submit, R.id.tv_register, R.id.tv_forget_psw} )
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.bt_submit:
                login();
                break;
            case R.id.tv_register:
//                EventBus.getDefault().post(new NotifyLoginRegisterChange(NotifyLoginRegisterChange.STATE_LOGIN, null));
                break;
            case R.id.tv_forget_psw:
                //忘记密码
//                Intent intent = new Intent(mActivity, MeManager.class);
//                intent.putExtra(BackFragmentActivity.TAG_FRAGMENT, ResetLoginPswFragment1.TAG);
//                Bundle bundle = new Bundle();
//                bundle.putString("phone", etPhone.getText().toString());
//                intent.putExtra(BaseFragmentActivity.ARGS, bundle);
//                mActivity.startActivity(intent);
                break;
        }
    }

    //登录操作
    private void login() {
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        if ( TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) ) {
            UiUtils.showToastInAnyThread("用户名或密码不能为空！");
            return;
        }
        showDialog("正在登录...");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
