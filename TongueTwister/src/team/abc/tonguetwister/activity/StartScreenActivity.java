package team.abc.tonguetwister.activity;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialogsamples.utils.T;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.animation.Jumper;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;
import team.abc.tonguetwister.init.TTSInit;
import team.abc.tonguetwister.sharedpreference.FirstStartUpJudgement;
import team.abc.tonguetwister.tools.NetWorkUtil;
import team.abc.tonguetwister.tools.RecordPermissionUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class StartScreenActivity extends Activity {
	private ImageView logoView;
	private TextView logoexpressText;
	private long exitTime = 0;

	//private final String NO_NETWORK = "未连接网络";
	private final String NO_NETWORK = "需要连接网络才可以使用哦~";
	//private final String DISABLE_NETWORK = "网络不可用";
	private final String DISABLE_NETWORK = "需要连接网络才可以使用哦~";
	private final String PREPARE_SUCCESS = "success";	
	private final String TAG = "StartScreenActivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen_layout);
		initialView();
	}

	private void initialView() {
		logoView = (ImageView) findViewById(R.id.logo_view);
		logoexpressText = (TextView) findViewById(R.id.logo_express);

		// 启动动画
		Jumper jumper = new Jumper(500, 30);
		jumper.attachToView(logoView);
	}

	@Override
	protected void onStart() {
		super.onStart();

		PrepareTask pt = new PrepareTask();
		pt.execute("");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class PrepareTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {

			// 网络连接检查
			if (!NetWorkUtil
					.isNetworkAvailable(MyApplication.getMyAppContext())) {
				return NO_NETWORK;

			}

			// TTS授权检查
			if (!TTSInit.initialOnlineModeTts()) {
				return DISABLE_NETWORK;
			}

			// 数据库初始化(写得有问题 需再测 20160711_zsc)
			TongueTwisterDetailsDb tongueTwisterDetailsDb = TongueTwisterDetailsDb
					.getDbInstance(StartScreenActivity.this);
			if (tongueTwisterDetailsDb.Db_getMorePassThrough().size() == 0) {
				tongueTwisterDetailsDb.passThroughAddMore();
			}
			

			/*SharedPreferences s = StartScreenActivity.this.getSharedPreferences(ISFIRSTRECORD,Context.MODE_PRIVATE);
			int count=s.getInt("coun", 0);
			if(count==0){//第一次开启
				RecordPermissionUtil.isHasPermission(StartScreenActivity.this);

				SharedPreferences.Editor editor = StartScreenActivity.this.getSharedPreferences(ISFIRSTRECORD, Context.MODE_PRIVATE).edit();
				editor.putInt("coun", 1);
				editor.commit();
			}*/
			

			return PREPARE_SUCCESS;
		}

		@Override
		protected void onPostExecute(String result) {
			if (PREPARE_SUCCESS.equals(result)) {
				startActivity(new Intent(StartScreenActivity.this,
						MainActivity.class));
				finish();

			} else {

				showMaterialDialog(result);

			}
		}

		private void showMaterialDialog(String msg) {
			final MaterialDialog dialog = new MaterialDialog(
					StartScreenActivity.this);

			dialog.btnNum(1).content(msg).btnText("确定")
					.showAnim(new BounceTopEnter())
					.dismissAnim(new SlideBottomExit()).show();

			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					StartScreenActivity.this.finish();

				}
			});
		}

	}

}
