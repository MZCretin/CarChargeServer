package com.cretin.www.carchargeserver.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cretin.www.carchargeserver.R;
import com.cretin.www.carchargeserver.base.BaseFragment;
import com.cretin.www.carchargeserver.base.ParentActivity;
import com.cretin.www.carchargeserver.fragment.TestFragment;
import com.cretin.www.carchargeserver.utils.UiUtils;
import com.cretin.www.carchargeserver.view.NoScrollViewPager;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ParentActivity {
    @Bind( R.id.view_pager )
    NoScrollViewPager viewPager;
    @Bind( R.id.rb_home )
    RadioButton rbHome;
    @Bind( R.id.rb_product )
    RadioButton rbProduct;
    @Bind( R.id.rb_me )
    RadioButton rbMe;
    @Bind( R.id.rg_group )
    RadioGroup rgGroup;
    private Map<Integer, BaseFragment> mFragments = new HashMap();
    private int currentPage = 0;

    @Override
    protected void initView(View view) {
        setContentView(R.layout.activity_main);
        //界面控件绑定注册
        ButterKnife.bind(this);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        //ViewPager缓存3个界面
        viewPager.setOffscreenPageLimit(3);
        //默认选择首页
        rgGroup.check(R.id.rb_home);
        initEvent();

        initData();
    }

    protected void initData() {

    }

    private void initEvent() {
        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch ( checkedId ) {
                    case R.id.rb_home:
                        currentPage = 0;
                        viewPager.setCurrentItem(currentPage, false);
                        break;
                    case R.id.rb_product:
                        currentPage = 1;
                        viewPager.setCurrentItem(currentPage, false);
                        break;
                    case R.id.rb_me:
                        currentPage = 2;
                        viewPager.setCurrentItem(currentPage, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private long lastBackTime;

    //在需要监听的activity中重写onKeyDown()。

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0 ) {
            long currentTime = System.currentTimeMillis();
            if ( currentTime - lastBackTime > 1 * 1000 ) {
                lastBackTime = currentTime;
                UiUtils.showToastInAnyThread("再按一次退出程序");
            } else {
                MainActivity.this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class MainAdapter extends FragmentStatePagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        // 每个条目返回的fragment
        //  0
        @Override
        public Fragment getItem(int position) {
            return createFragment(position);
        }

        // 一共有几个条目
        @Override
        public int getCount() {
            return 3;
        }

        // 返回每个条目的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    public BaseFragment createFragment(int position) {
        BaseFragment fragment;
        fragment = mFragments.get(position);
        //在集合中取出来Fragment
        if ( fragment == null ) {  //如果再集合中没有取出来 需要重新创建
            if ( position == 0 ) {
                fragment = new TestFragment();
            } else if ( position == 1 ) {
                fragment = new TestFragment();
            } else if ( position == 2 ) {
                fragment = new TestFragment();
            }
            if ( fragment != null ) {
                mFragments.put(position, fragment);// 把创建好的Fragment存放到集合中缓存起来
            }
            return fragment;
        } else {
            return fragment;
        }
    }
}
