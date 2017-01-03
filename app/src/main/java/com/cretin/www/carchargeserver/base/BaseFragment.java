package com.cretin.www.carchargeserver.base;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bubu.status.StatusLayout;
import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.utils.ViewUtils;
import com.cretin.www.carchargeserver.view.CustomProgressDialog;
import com.cretin.www.carchargeserver.view.MyStatusView;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by cretin on 16/10/27.
 */

public abstract class BaseFragment extends Fragment {
    private TextView tvMainTitle;
    private ImageView ivMainBack;
    private ImageView ivMainRight;
    private TextView tvMainRight;

    StatusLayout statusLayout;
    MyStatusView statusView;

    private LinearLayout container;

    private View lineDivider;

    private RelativeLayout relaLoadContainer;
    private TextView tvLoadingMsg;

    private CustomProgressDialog dialog;

    protected ParentActivity mActivity;

    private OnTitleAreaCliclkListener onTitleAreaCliclkListener;

    private LinearLayout llMainTitle;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_base_activity, null);
        if ( (( ParentActivity ) getActivity()).isKitkat ) {
            view.findViewById(R.id.ll_main_title).setPadding(0, ViewUtils.getStatusBarHeights() - 20, 0, 0);
        }

        //自己消费点击事件 防止事件穿透
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        initHeadView(view);
        initContentView(view, savedInstanceState);
        initData();
        return view;
    }

    public TextView getTvMainRight() {
        return tvMainRight;
    }

    //获取宿主Activity
    protected ParentActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = ( ParentActivity ) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if ( this.mCompositeSubscription != null ) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    private AnimationDrawable animationDrawable;
    private CompositeSubscription mCompositeSubscription;

    protected void addSubscription(Subscription s) {
        if ( this.mCompositeSubscription == null ) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    private void initContentView(View view, Bundle savedInstanceState) {
        container = ( LinearLayout ) view.findViewById(R.id.ll_content);
        llMainTitle = ( LinearLayout ) view.findViewById(R.id.ll_main_title);

        statusView = MyStatusView.getInstance(getActivity(), "这里竟然什么都没有", new MyStatusView.onRetryClickLister() {
            @Override
            public void onRetryClick() {

            }
        });

        View v = View.inflate(getActivity(), getLayoutId(), null);
        statusLayout =
                new StatusLayout.Builder().setContentView(container).setStatusView(statusView).build();
        ButterKnife.bind(this, v);
        container.addView(v, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initView(v, savedInstanceState);
    }


    //隐藏正在加载视图
    public void hidProgressView() {
        if ( relaLoadContainer != null )
            relaLoadContainer.setVisibility(View.GONE);
        if ( animationDrawable != null )
            animationDrawable.stop();
    }

    //隐藏头部控件
    public void hidTitleView() {
        if ( llMainTitle != null )
            llMainTitle.setVisibility(View.GONE);
    }

    public View getHeadView() {
        return llMainTitle;
    }

    //隐藏返回按钮
    public void hidBackBtn() {
        if ( ivMainBack != null )
            ivMainBack.setVisibility(View.GONE);
    }

    //显示正在加载视图
    public void showProgressView() {
        if ( relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE )
            relaLoadContainer.setVisibility(View.VISIBLE);
        if ( animationDrawable != null )
            animationDrawable.start();
    }

    //显示加载错误
    public void showErrorView() {
        if ( relaLoadContainer != null && relaLoadContainer.getVisibility() == View.GONE )
            relaLoadContainer.setVisibility(View.VISIBLE);
        if ( animationDrawable != null )
            animationDrawable.stop();
        tvLoadingMsg.setText("加载错误");
    }

    //初始化头部视图
    private void initHeadView(View view) {
        tvMainTitle = ( TextView ) view.findViewById(R.id.tv_title_info);
        ivMainBack = ( ImageView ) view.findViewById(R.id.iv_back);
        ivMainRight = ( ImageView ) view.findViewById(R.id.iv_right);
        tvMainRight = ( TextView ) view.findViewById(R.id.tv_right);
        lineDivider = view.findViewById(R.id.line_divider);

        ivMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( leftBtnClickable )
                    if ( getActivity() instanceof BackFragmentActivity ) {
                        (( BackFragmentActivity ) getActivity()).removeFragment();
                    } else {
                        getActivity().finish();
                    }
                if ( onTitleAreaCliclkListener != null )
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        ivMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onTitleAreaCliclkListener != null )
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
        tvMainRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onTitleAreaCliclkListener != null )
                    onTitleAreaCliclkListener.onTitleAreaClickListener(v);
            }
        });
    }

    private boolean leftBtnClickable = true;

    //移除左边按钮点击事件
    public void removeLeftListener() {
        leftBtnClickable = false;
    }

    /**
     * 显示加载对话框
     *
     * @param msg
     */
    public void showDialog(String msg) {
        if ( dialog == null ) {
            dialog = CustomProgressDialog.createDialog(getActivity());
            if ( msg != null && !msg.equals("") ) {
                dialog.setMessage(msg);
            }
        }
        dialog.show();
    }

    //隐藏title栏的分界线
    public void hidLineDivider() {
        if ( lineDivider.getVisibility() == View.VISIBLE )
            lineDivider.setVisibility(View.GONE);
    }

    /**
     * 关闭对话框
     */
    public void stopDialog() {
        if ( dialog != null && dialog.isShowing() ) {
            dialog.dismiss();
        }
    }

    public void setOnTitleAreaCliclkListener(OnTitleAreaCliclkListener onTitleAreaCliclkListener) {
        this.onTitleAreaCliclkListener = onTitleAreaCliclkListener;
    }

    //设置Title
    protected void setMainTitle(String title) {
        if ( !TextUtils.isEmpty(title) )
            tvMainTitle.setText(title);
    }

    //设置Title背景色
    protected void setMainTitleBackground(int color) {
        llMainTitle.setBackgroundColor(color);
    }

    //设置TitleColor
    protected void setMainTitleColor(String titleColor) {
        if ( !TextUtils.isEmpty(titleColor) )
            setMainTitleColor(Color.parseColor(titleColor));
    }

    //设置TitleColor
    protected void setMainTitleColor(int titleColor) {
        tvMainTitle.setTextColor(titleColor);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(int tvRightColor) {
        tvMainRight.setTextColor(tvRightColor);
    }

    //设置左边ImageView的Visiable
    protected void setMainIvLeftVisable(int visable) {
        ivMainBack.setVisibility(visable);
    }

    //设置右边TextView颜色
    protected void setMainTitleRightColor(String tvRightColor) {
        if ( !TextUtils.isEmpty(tvRightColor) )
            setMainTitleRightColor(Color.parseColor(tvRightColor));
    }

    //设置右边TextView大小
    protected void setMainTitleRightSize(int size) {
        tvMainRight.setTextSize(size);
    }

    //设置右边TextView内容
    protected void setMainTitleRightContent(String content) {
        if ( !TextUtils.isEmpty(content) ) {
            if ( tvMainRight.getVisibility() == View.GONE )
                tvMainRight.setVisibility(View.VISIBLE);
            tvMainRight.setText(content);
        }
    }

    //显示右边TextView
    public void showRightTextView() {
        if ( tvMainRight.getVisibility() == View.GONE )
            tvMainRight.setVisibility(View.VISIBLE);
    }

    //隐藏右边TextView
    public void hidRightTextView() {
        if ( tvMainRight.getVisibility() == View.VISIBLE )
            tvMainRight.setVisibility(View.GONE);
    }

    //设置左边ImageView资源
    protected void setMainLeftIvRes(int res) {
        if ( ivMainBack.getVisibility() == View.GONE )
            ivMainBack.setVisibility(View.VISIBLE);
        ivMainBack.setImageResource(res);
    }

    //设置又边ImageView资源
    protected void setMainRightIvRes(int res) {
        if ( ivMainRight.getVisibility() == View.GONE )
            ivMainRight.setVisibility(View.VISIBLE);
        ivMainRight.setImageResource(res);
    }

    public interface OnTitleAreaCliclkListener {
        void onTitleAreaClickListener(View view);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View contentView, Bundle savedInstanceState);

    protected abstract void initData();
}
