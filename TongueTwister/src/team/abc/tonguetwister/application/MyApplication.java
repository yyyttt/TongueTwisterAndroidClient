package team.abc.tonguetwister.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class MyApplication extends Application {
	private static MyApplication mcontext;

	//合代码添加
	/*public static MyApplication getInstance()

	{
		return mcontext;
	}*/

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
}