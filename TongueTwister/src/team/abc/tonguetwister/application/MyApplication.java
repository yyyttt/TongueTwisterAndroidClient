package team.abc.tonguetwister.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MyApplication extends Application {
	private static MyApplication mcontext;
	private static List<Activity> activitiesList = new ArrayList<Activity>();
	// 合代码添加
	/*
	 * public static MyApplication getInstance()
	 * 
	 * { return mcontext; }
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mcontext = this;
	}

	public static Context getMyAppContext() {
		return mcontext;
	}

	public static Resources getAppResources() {
		return mcontext.getResources();
	}

	public static String getAppString(int id) {
		return getAppResources().getString(id);
	}

	// 添加activity到list
	public  static void addActivity(Activity activity) {
		if (!activitiesList.contains(activity)) {
			activitiesList.add(activity);
		}

	}

	// 退出时杀掉所有Activity
	public static void exit() {
		if (!activitiesList.isEmpty()) {
			for (Activity activity : activitiesList) {
				activity.finish();
			}
		}
		System.exit(0);
	}
}