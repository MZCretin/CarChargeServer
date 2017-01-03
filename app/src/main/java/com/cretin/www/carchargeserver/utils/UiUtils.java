package com.cretin.www.carchargeserver.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.Toast;

import com.cretin.www.carchargeserver.BaseApplication;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UiUtils {

	public static Toast mToast;
	/**
	 * 获取到字符数组
	 * @param tabNames  字符数组的id
	 */
	public static String[] getStringArray(int tabNames) {
		return getResource().getStringArray(tabNames);
	}

	public static Resources getResource() {
		return BaseApplication.getContext().getResources();
	}
	public static Context getContext(){
		return BaseApplication.getContext();
	}
	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		LogUtils.i(scale+"scale");
		return (int) (dip * scale + 0.5f);
	}

	/** px转换dip */

	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	/**
	 * 把Runnable 方法提交到主线程运行
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// 在主线程运行
		if(android.os.Process.myTid()==BaseApplication.getMainTid()){
			runnable.run();
		}else{
			//获取handler
			BaseApplication.getHandler().post(runnable);
		}
	}
	public static int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResource().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResource().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	public static View inflate(int id) {
		return View.inflate(getContext(), id, null);
	}

	public static Drawable getDrawalbe(int id) {
		return getResource().getDrawable(id);
	}

	public static int getDimens(int dimenID) {
		return (int) getResource().getDimension(dimenID);
	}
	/**
	 * 延迟执行 任务
	 * @param run   任务
	 * @param time  延迟的时间
	 */
	public static void postDelayed(Runnable run, int time) {
		BaseApplication.getHandler().postDelayed(run, time); // 调用Runable里面的run方法
	}
	/**
	 * 取消任务
	 * @param auToRunTask
	 */
	public static void cancel(Runnable auToRunTask) {
		BaseApplication.getHandler().removeCallbacks(auToRunTask);
	}
	/**
	 * 可以打开activity
	 * @param intent
	 */
	public static void startActivity(Intent intent) {
		// 如果不在activity里去打开activity  需要指定任务栈  需要设置标签
//		if(BaseActivity.activity==null){
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			getContext().startActivity(intent);
//		}else{
//			BaseActivity.activity.startActivity(intent);
//		}
	}

	/**
	 * 显示土司
	 * @param text
	 */
	public static void showToastInAnyThread(final String text){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public static void removeParent(View v){
		//  先找到爹 在通过爹去移除孩子
		ViewParent parent = v.getParent();
		//所有的控件 都有爹  爹一般情况下 就是ViewGoup
		if(parent instanceof ViewGroup){
			ViewGroup group=(ViewGroup) parent;
			group.removeView(v);
		}
	}

	public static String getFormatDate(String stamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.parseLong(stamp) * 1000));
	}

	public static void showToast(Context mContext, String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}

	public static String getFormatMoney(String money) {
		NumberFormat currency = NumberFormat.getCurrencyInstance();    //建立货币格式化引用
		return currency.format(money);
	}
	public static String getFormatMoney(double money) {
		NumberFormat currency = NumberFormat.getCurrencyInstance();    //建立货币格式化引用
		return currency.format(money);
	}
	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	//设置一个字符串指定位置的Span样式
	public static SpannableString setPositionSpannable(String oldStr, int start, int end, int color) {
		//String startStr = oldStr.substring(0, start);
		//String endStr = oldStr.substring(end, oldStr.length());
		String midStr = oldStr.substring(start, end);
		SpannableString sp = new SpannableString(oldStr);
		sp.setSpan(new ForegroundColorSpan(UiUtils.getResource().getColor(color)),
				start, start+midStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return sp;
	}

	/**
	 * 验证手机号码
	 *
	 * @param mobileNumber
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		String url = "^[1][34578][0-9]{9}";
		try {
			// Pattern regex = Pattern
			// .compile("^(((13[0-9])|(14[0-9])|(17[0-9])|(15([0-9]))|(18[0-9]))\\d{8})|(17[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Pattern regex = Pattern.compile(url);
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
