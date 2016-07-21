package team.abc.tonguetwister.activity;

import java.util.List;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.constant.StudyMode;
import team.abc.tonguetwister.tools.TTOperation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StudyModeChooseActivity extends Activity implements
		OnClickListener {

	private RelativeLayout rlTabularForm, rlRandom, rlCollections;
	private RelativeLayout rlAllMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//去掉title_bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_study_mode_choose);
		initView();
	}

	private void initView() {
		rlTabularForm = (RelativeLayout) findViewById(R.id.rl_tabularform);
		rlRandom = (RelativeLayout) findViewById(R.id.rl_Random);
		rlCollections = (RelativeLayout) findViewById(R.id.rl_Collections);
		rlAllMode = (RelativeLayout) findViewById(R.id.rl_all_modes);
		rlTabularForm.setOnClickListener(this);
		rlRandom.setOnClickListener(this);
		rlCollections.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		rlAllMode.startLayoutAnimation();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		String mode = StudyMode.class.getName();
		switch (v.getId()) {
		case R.id.rl_tabularform:

			intent = new Intent(this, StudyTTChooseActivity.class);
			//intent.putExtra(mode, StudyMode.MODE_LIST);
			StudyMode.mode = StudyMode.MODE_LIST; 
			startActivity(intent);

			break;

		case R.id.rl_Random:
			intent = new Intent(this, StudyActivity.class);
			//intent.putExtra(mode, StudyMode.MODE_RANDOM);
			StudyMode.mode = StudyMode.MODE_RANDOM; 
			startActivity(intent);
			break;

		case R.id.rl_Collections:
			
			List<TongueTwister> list = TTOperation.getCollectionTT();
			if(null == list || list.isEmpty()){
				Toast.makeText(this,"暂无收藏",Toast.LENGTH_SHORT).show();
			}else{
				intent = new Intent(this, StudyTTChooseActivity.class);
				//intent.putExtra(mode, StudyMode.MODE_COLLECTION);
				StudyMode.mode = StudyMode.MODE_COLLECTION; 
				startActivity(intent);				
			}
			
			break;
		default:
			break;
		}

	}

}
