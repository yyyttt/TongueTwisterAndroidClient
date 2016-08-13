package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
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
			break;
		case R.id.rl_ranking:
			startActivity(new Intent(PkStartActivity.this, RankingActivity.class));
			break;
		default:
			break;

		}
	}
	
	
}
