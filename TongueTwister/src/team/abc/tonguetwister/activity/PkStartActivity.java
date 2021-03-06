package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.sharedpreference.FirstStartUpJudgement;
import team.abc.tonguetwister.tools.RecordPermissionUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PkStartActivity extends Activity implements OnClickListener {
	private TextView tvStart;
	private RelativeLayout rlButtonsPk;
	private RelativeLayout rlStartPk;
	private RelativeLayout rlRanking;
	private LinearLayout lvContent;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start_pk);
		initialView();
		
		//判断是否首次登陆要录音权限
		if(FirstStartUpJudgement.isFirstNeedRecord()){
			
			RecordPermissionUtil.isHasPermission(this);
			
		}

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		rlButtonsPk.startLayoutAnimation();
		super.onResume();
	}

	private void initialView() {
		tvStart = (TextView) findViewById(R.id.tv_start);
		lvContent = (LinearLayout) findViewById(R.id.lv_content);
		rlButtonsPk = (RelativeLayout) findViewById(R.id.rl_buttons_pk);
		rlStartPk = (RelativeLayout) findViewById(R.id.rl_start_pk);
		rlRanking= (RelativeLayout) findViewById(R.id.rl_ranking);
		rlStartPk.setOnClickListener(this);
		rlRanking.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_start_pk:
			startActivity(new Intent(PkStartActivity.this, PkActivity.class));
			/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();*/
			break;
		case R.id.rl_ranking:
			startActivity(new Intent(PkStartActivity.this, RankingActivity.class));
			/*overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();*/
			break;
		default:
			break;

		}
	}
	
	/*
	 * 手机键盘的操作
	 */
	/*public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(PkStartActivity.this,MainActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return false;
	};*/
}
