package team.abc.tonguetwister.activity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import team.abc.bean.UserInfo;
import team.abc.ihessian.IUserInfoHessian;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.RankingListAdapt;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.sharedpreference.UserInfoSharedPreference;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.caucho.hessian.client.HessianProxyFactory;

public class RankingActivity extends Activity {
	private ListView listView;
	private List<UserInfo> listItems;
	private static final String TAG = "RankingActivity";
	private UserInfo localUserInfo;
	private RankingListAdapt adapter;
	private int localUserRanking;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);

		initView();
		initData();

	}

	private void initData() {

		localUserInfo = UserInfoSharedPreference.getUserInfo();

		// HessianProxyFactory factory = new HessianProxyFactory();
		new UserInfoDataAchieveTask().execute();

	}

	private void initView() {
		listView = (ListView) findViewById(R.id.list_ranking);
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

	public class UserInfoDataAchieveTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			adapter.notifyDataSetChanged();

			Toast.makeText(RankingActivity.this,
					"本地用户的排名为: " + localUserRanking, Toast.LENGTH_LONG).show();

			super.onPostExecute(result);

		}

		@Override
		protected Void doInBackground(Void... params) {
			IUserInfoHessian userInfoHessian = null;
			HessianProxyFactory factory = new HessianProxyFactory();
			try {
				factory.setHessian2Reply(false);
				userInfoHessian = (IUserInfoHessian) factory.create(
						IUserInfoHessian.class, URLConstant.USER_INFO_URL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 插入本地用户
			userInfoHessian.insertOrUpdateUser(localUserInfo);

			// 获取列表
			List<UserInfo> list = userInfoHessian.getUsersOrderByRanking(6);
			listItems.addAll(list);
			Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<" + listItems);

			// 获取本地用户排名
			localUserRanking = userInfoHessian.getPassNum(localUserInfo);
			return null;
		}

	}

}
