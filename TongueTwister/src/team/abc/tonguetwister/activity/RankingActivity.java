package team.abc.tonguetwister.activity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import team.abc.bean.UserInfo;
import team.abc.ihessian.IUserInfoHessian;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.RankingListAdapt;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.Gender;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.sharedpreference.UserInfoSharedPreference;
import team.abc.tonguetwister.tools.NetWorkUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caucho.hessian.client.HessianProxyFactory;

public class RankingActivity extends Activity {
	private ListView listView;
	private List<UserInfo> listItems;
	private static final String TAG = "RankingActivity";
	public static final int TEN_ITEMS = 10;
	private UserInfo localUserInfo;
	private RankingListAdapt adapter;
	private int localUserRanking = -1;
	private boolean hasLogin;
	private int passNum;
	private ImageView ivRankUser;
	private TextView tvResult;
	private ProgressBar proBarLoadingList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);

		passNum = getIntent().getIntExtra("number", -1);
		Log.i(TAG, "passNum="+passNum);
		initView();
		initData();

	}

	private void initData() {

		localUserInfo = UserInfoSharedPreference.getUserInfo();

		if (localUserInfo == null) {
			hasLogin = false;
			//未登录时将头像设置成secret
			ivRankUser.setImageResource(R.drawable.head_portrait_secret);
		} else {
			hasLogin = true;
			//初始化头像
			setHeadProtrait(localUserInfo.getUserGender());
			
		}

		if (passNum != -1 && hasLogin) {
			localUserInfo.setChallengePassNum(passNum);
			UserInfoSharedPreference.storeUserInfo(localUserInfo);			
		}
			

		if(NetWorkUtil.isNetworkAvailable(this)){			
			new UserInfoDataAchieveTask().execute();
		}else{
			Toast.makeText(this, Constant.EXCEPTION_NETWORK, Toast.LENGTH_SHORT).show();
			proBarLoadingList.setVisibility(View.GONE);
		}

	}

	private void initView() {
		ivRankUser = (ImageView) findViewById(R.id.iv_rank_user);
		listView = (ListView) findViewById(R.id.list_ranking);
		tvResult = (TextView) findViewById(R.id.tv_result);
		proBarLoadingList = (ProgressBar)findViewById(R.id.proBar_loading_list);
		
		// 获所有将要绑定的用户数据并设置到listItems中
		listItems = new ArrayList<UserInfo>();
		adapter = new RankingListAdapt(RankingActivity.this, listItems,
				R.layout.ranking_list_item);
		listView.setAdapter(adapter);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {

		listView.startLayoutAnimation();
		
		super.onResume();
	}

	public class UserInfoDataAchieveTask extends AsyncTask<Void, Void, String> {

		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(result != null){//说明有异常
				Toast.makeText(RankingActivity.this, result, Toast.LENGTH_SHORT).show();
				proBarLoadingList.setVisibility(View.GONE);
			}else{
				//通知列表数据更新。
				adapter.notifyDataSetChanged();
				listView.startLayoutAnimation();
				//将结果显示在界面上。
				setResult();				
			}
			super.onPostExecute(result);
		}

		
		@Override
		protected String doInBackground(Void... params) {
			IUserInfoHessian userInfoHessian = null;
			HessianProxyFactory factory = new HessianProxyFactory();
			//factory.setOverloadEnabled(true);
			factory.setHessian2Reply(false);
			try {
				userInfoHessian = (IUserInfoHessian) factory.create(
						IUserInfoHessian.class, URLConstant.USER_INFO_URL);
				// 插入本地用户
				if (hasLogin && (passNum != -1)) {
					userInfoHessian.insertOrUpdateUser(localUserInfo);
				}

				// 获取列表
				List<UserInfo> list = userInfoHessian.getUsersOrderByRanking(TEN_ITEMS);
				listItems.addAll(list);

				// 获取本地用户排名
				if (hasLogin) {
					localUserRanking = userInfoHessian.getPassNum(localUserInfo);
				}
				
			} catch (Exception e) {

				Log.i(TAG, Constant.EXCEPTION_NETWORK);
				
				return Constant.EXCEPTION_NETWORK;

			}

			return null;
		}

	}

	private void setHeadProtrait(int gender) {

		switch (gender) {
		case Gender.MALE:

			ivRankUser.setImageResource(R.drawable.head_portrait_male);

			break;
		case Gender.FEMALE:
			ivRankUser.setImageResource(R.drawable.head_portrait_female);

			break;
		case Gender.SECRET:

			ivRankUser.setImageResource(R.drawable.head_portrait_secret);
			break;

		default:
			
			break;
		}

	}

	public void setResult() {

		//有数据时，让其消失
		proBarLoadingList.setVisibility(View.GONE);
		
		//未登录，未挑战
		if(!hasLogin && (passNum == -1)){
			tvResult.setText("提示：您尚未登陆！");
			return;
		}
		
		//已登录，未挑战
		if(hasLogin && (passNum == -1)){
			passNum = localUserInfo.getChallengePassNum();
			if(passNum == 0){//首次安装客户端，但以前登陆挑战过。				
				tvResult.setText("目前排名第"+localUserRanking+"名");
			}else{				
				tvResult.setText("您上次成功挑战"+localUserInfo.getChallengePassNum()+"关     "+"目前排名第"+localUserRanking+"名");
			}
			
			return;
		}
		
		//未登录，已挑战
		if(!hasLogin && (passNum != -1)){
			tvResult.setText("您成功挑战"+passNum+"关   快去登录吧！");
			return;
		}
		
		//已登录，已挑战
		if(hasLogin && (passNum != -1)){
			tvResult.setText("您成功挑战"+passNum+"关    "+"目前排名第"+localUserRanking+"名");
			return;
		}
		
		return;
		
	}
	/*
	 * 手机键盘的操作
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(RankingActivity.this,PkStartActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return false;
	};
}
