package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PkActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pk);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}
