package team.abc.tonguetwister.activity;

import java.util.ArrayList;
import java.util.List;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.RankingListAdapt;
import team.abc.tonguetwister.bean.UserInfo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

public class RankingActivity extends Activity {
	private ListView listView;
	private static final String TAG = "RankingActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);
		initial();
		// 获所有将要绑定的用户数据并设置到listItems中
		List<UserInfo> listItems = getData();
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<" + listItems);
		RankingListAdapt adapter = new RankingListAdapt(RankingActivity.this,
				listItems, R.layout.ranking_list_item);
		((ListView) listView).setAdapter(adapter);
	}

	private List<UserInfo> getData() {
		// TODO Auto-generated method stub
		List<UserInfo> list = new ArrayList<UserInfo>();
		UserInfo user1 = new UserInfo(1, "18292001111", 0, 9);
		UserInfo user2 = new UserInfo(2, "18292002222", 0, 8);
		UserInfo user3 = new UserInfo(3, "18292003333", 0, 7);
		UserInfo user4 = new UserInfo(4, "18292004444", 0, 6);
		UserInfo user5 = new UserInfo(5, "18292005555", 0, 5);
		list.add(user1);
		list.add(user2);
		list.add(user3);
		list.add(user4);
		list.add(user5);
		return list;
	}

	private void initial() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.list_ranking);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}
