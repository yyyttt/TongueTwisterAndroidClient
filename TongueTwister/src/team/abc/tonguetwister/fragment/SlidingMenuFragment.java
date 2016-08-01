package team.abc.tonguetwister.fragment;

/**
 * 
 * @author zsc
 * 
 */

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.activity.AboutUsActivity;
import team.abc.tonguetwister.activity.LoginChooseActivity;
import team.abc.tonguetwister.activity.MainActivity;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.constant.Gender;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.javascriptobject.UserLoginObject;
import team.abc.tonguetwister.javascriptobject.UserLogoutObject;
import team.abc.tonguetwister.thread.UpdateChecker;
import team.abc.tonguetwister.tools.ShareUtil;
import team.abc.tonguetwister.widget.UpdateDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SlidingMenuFragment extends Fragment implements OnClickListener {

	private View currentView;
	private String userName, userID;
	private int gender;
	private ImageView ivHeadPortrait;
	private TextView tvUserName;
	private TextView tvLogout;
	private WebView wvLogout;
	private TextView tvAboutus;
	private TextView tvUpdatingOnline;
	private TextView tvShareOnline;
	private LinearLayout lvLogin;
	public ListView listview;

	public SlidingMenuFragment() {

	}

	public View getCurrentView() {
		return currentView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		currentView = inflater
				.inflate(R.layout.fragment_menu, container, false);

		initView();// 初始化控件。

		if (userName != null) {
			tvUserName.setText(userName);
			Toast.makeText(MyApplication.getMyAppContext(), userName + "登陆成功",
					Toast.LENGTH_SHORT).show();
			allowLogin(false);
		} else {
			allowLogin(true);
		}

		switch (gender) {
		case Gender.SECRET:
			ivHeadPortrait.setImageResource(R.drawable.head_portrait_secret);
			break;

		case Gender.MALE:
			ivHeadPortrait.setImageResource(R.drawable.head_portrait_male);

			break;

		case Gender.FEMALE:

			ivHeadPortrait.setImageResource(R.drawable.head_portrait_female);
			break;
		default:
			break;
		}

		return currentView;

	}

	private void initView() {
		tvUserName = (TextView) currentView.findViewById(R.id.tv_user_name);
		tvLogout = (TextView) currentView.findViewById(R.id.tv_logout);
		wvLogout = (WebView) currentView.findViewById(R.id.wv_logout);
		tvAboutus = (TextView) currentView.findViewById(R.id.tv_about_us);
		tvUpdatingOnline = (TextView) currentView
				.findViewById(R.id.tv_updating_online);
		tvShareOnline = (TextView) currentView
				.findViewById(R.id.tv_share_online);
		lvLogin = (LinearLayout) currentView.findViewById(R.id.lv_login);
		ivHeadPortrait = (ImageView) currentView
				.findViewById(R.id.iv_head_portrait);
		tvAboutus.setOnClickListener(this);
		tvUpdatingOnline.setOnClickListener(this);
		tvShareOnline.setOnClickListener(this);
		tvLogout.setOnClickListener(this);
		lvLogin.setOnClickListener(this);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		/*
		 * tvUserName.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * startActivity(new Intent(getActivity(), LoginChooseActivity.class));
		 * // getActivity().finish();
		 * 
		 * } });
		 */

		/*
		 * tvLogout.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * userLogout();
		 * 
		 * }
		 * 
		 * });
		 */
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getActivity().getIntent();
		userName = intent.getStringExtra("userName");
		userID = intent.getStringExtra("userID");
		gender = intent.getIntExtra("gender", Gender.SECRET);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private void userLogout() {

		WebSettings webSettings = wvLogout.getSettings();
		webSettings.setJavaScriptEnabled(true);

		wvLogout.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);

				return true;
			}

			// 网页载入异常时……
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				Toast.makeText(MyApplication.getMyAppContext(), "退出失败",
						Toast.LENGTH_SHORT).show();
			}

		});

		wvLogout.loadUrl(URLConstant.LOGOUT_URL);
		wvLogout.addJavascriptInterface(new UserLogoutObject(this),
				"userLogoutObject");

	}

	/*
	 * 当用户退出登录成功时（javascript调用UserLogoutObejct的方法）的回掉函数。
	 */
	public void wipeUserInfo() {

		Toast.makeText(MyApplication.getMyAppContext(), "退出成功",
				Toast.LENGTH_SHORT).show();

		// 删除浏览器cookie
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();

		startActivity(new Intent(this.getActivity(), MainActivity.class));
		this.getActivity().finish();

	}

	private void allowLogin(Boolean b) {
		lvLogin.setEnabled(b);
		tvLogout.setEnabled(!b);
		if (b) {
			tvLogout.setTextColor(MyApplication.getAppResources().getColor(
					R.color.deepsmokygray1));
		} else {
			tvLogout.setTextColor(MyApplication.getAppResources().getColor(
					R.color.white));
		}
	}

	// yt
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_about_us:
			startActivity(new Intent(getActivity(), AboutUsActivity.class));
			break;
		case R.id.tv_updating_online:
			UpdateChecker.checkForDialog(this.getActivity());
			break;
		case R.id.lv_login:
			startActivity(new Intent(getActivity(), LoginChooseActivity.class));
			break;
		case R.id.tv_share_online:
			ShareUtil.shareTo(getActivity(), "挑战绕口令？尽在吧嗒绕口令-让你的舌头快快舞动起来~"+URLConstant.SHARE_URL);
			break;
		case R.id.tv_logout:
			userLogout();
			break;
		}
	}

}
