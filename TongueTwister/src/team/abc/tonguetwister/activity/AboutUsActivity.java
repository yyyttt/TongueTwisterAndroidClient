package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class AboutUsActivity extends Activity{
	private TextView tvContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.actiivity_about_us);
		initialView();
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	private void initialView(){
		tvContent = (TextView) findViewById(R.id.tv_about_us);
	}

}
