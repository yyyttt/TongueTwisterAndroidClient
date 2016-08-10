package team.abc.tonguetwister.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.RankingListAdapt;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

public class RankingActivity extends Activity {
	 private ListView lvRanking;
	 private List<Map<String, Object>> listItem;
	 private static final String TAG = "RankingActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ranking);
		initial();
		//获取将要绑定的数据设置到data中  
		listItem=getData();
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<"+listItem);
        RankingListAdapt adapter = new RankingListAdapt(listItem, RankingActivity.this);  
        lvRanking.setAdapter(adapter); 
	}
	private List<Map<String, Object>> getData()  
    {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        Map<String, Object> map;  
        for(int i=0;i<10;i++)  
        {  
            map = new HashMap<String, Object>();  
            map.put("num", "1");
            map.put("img", R.drawable.head_portrait_secret);  
            map.put("name", "18264981885");  
            map.put("done", "10");  
            list.add(map);  
        }  
        return list;  
    }  

	private void initial() {
		// TODO Auto-generated method stub
		lvRanking = (ListView) findViewById(R.id.list_ranking);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
