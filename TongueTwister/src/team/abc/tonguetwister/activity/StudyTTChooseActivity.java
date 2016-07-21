package team.abc.tonguetwister.activity;

import java.util.List;

import com.baidu.tts.answer.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.StudyListAdapter;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.Level;
import team.abc.tonguetwister.constant.StudyMode;
import team.abc.tonguetwister.tools.CopyFromAssetsToSdcard;
import team.abc.tonguetwister.tools.GroupTitleUtil;
import team.abc.tonguetwister.tools.TTOperation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StudyTTChooseActivity extends Activity {
	private ListView lvTongueTwisters;
	private List<TongueTwister> list;
	private String mSampleDirPath;
	private SpeechSynthesizer mSpeechSynthesizer;
	private static final String TAG = "StudyChooseActivity";
	private boolean isCollectionMode = false;
	private StudyListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// yt
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_study_tonguetwister_choose);

		lvTongueTwisters = (ListView) findViewById(R.id.lv_tonguetwisters);

		/*if (getIntent().getStringExtra(StudyMode.class.getName()).equals(
				StudyMode.MODE_COLLECTION)) {
			isCollectionMode = true;
		}*/
		
		if(StudyMode.mode == StudyMode.MODE_COLLECTION){
			isCollectionMode = true;
		}
		

		if (isCollectionMode) {
			list = TTOperation.getCollectionTT();
			GroupTitleUtil.insertCollectionGroupTitle(list);
		} else {
			list = TTOperation.getAllTT();
			GroupTitleUtil.insertGroupTitle(list);
		}
		
		//装入adapter
		listAdapter = new StudyListAdapter(list,this);		
		lvTongueTwisters.setAdapter(listAdapter);

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
		/*if (isCollectionMode) {
			list = TTOperation.getCollectionTT();
			GroupTitleUtil.insertCollectionGroupTitle(list);
		} else {
			list = TTOperation.getAllTT();
			GroupTitleUtil.insertGroupTitle(list);
		}
		listAdapter.notifyDataSetChanged();
		listAdapter.notifyDataSetInvalidated();*/
		
		
	}

}
