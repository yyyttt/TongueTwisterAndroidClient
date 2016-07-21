package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class LoginChooseActivity extends Activity implements OnClickListener {

	private RelativeLayout lvHuawei, lvQQ, lvWeixin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 去掉title_bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login_choose_layout);
		initView();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lv_huawei_login:

			startActivity(new Intent(LoginChooseActivity.this,
					WebLoginActivity.class));
			finish();
			break;

		default:

			break;
		}

	}

	private void initView() {
		lvHuawei = (RelativeLayout) findViewById(R.id.lv_huawei_login);
		lvQQ = (RelativeLayout) findViewById(R.id.lv_qq_login);
		lvWeixin = (RelativeLayout) findViewById(R.id.lv_weixin_login);
		lvHuawei.setOnClickListener(this);
		lvQQ.setOnClickListener(this);
		lvWeixin.setOnClickListener(this);

	}

}
