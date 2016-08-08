package team.abc.tonguetwister.activity;

import java.io.File;

import com.audioplayer.demo.AudioParam;
import com.audioplayer.demo.AudioPlayer;
import com.audioplayer.demo.PcmRelated;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.PathConstant;

public class PassThroughGradeActivity extends Activity implements
		OnClickListener {
	private TextView tv_show_result;
	private ImageView iv_show_result;
	private Button btn_record, iv_repeat, iv_next;
	private float ratingNum;
	private int number, flagPCM = 0;
	private String filePath;
	private AudioPlayer mAudioPlayer; // 播放器
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pass_through_grade);

		Bundle bd = getIntent().getExtras();
		ratingNum = bd.getFloat("ratingNum");
		number = bd.getInt("number");

		initAudioPlay();
		initView();
		initPCMData();

		PassThroughGradeActivity.this.setFinishOnTouchOutside(false);

	}

	private void initView() {
		tv_show_result = (TextView) findViewById(R.id.tv_show_result);
		iv_show_result = (ImageView) findViewById(R.id.iv_show_result);
		iv_repeat = (Button) findViewById(R.id.iv_repeat);
		iv_repeat.setOnClickListener(this);
		iv_next = (Button) findViewById(R.id.iv_next);
		iv_next.setOnClickListener(this);
		btn_record = (Button) findViewById(R.id.btn_record);
		btn_record.setOnClickListener(this);

		if (ratingNum >= 3f) {
			iv_show_result.setBackgroundResource(R.drawable.smile);
			tv_show_result.setText("恭喜你");
			iv_next.setVisibility(View.VISIBLE);
		} else {
			iv_show_result.setBackgroundResource(R.drawable.scary);
			tv_show_result.setText("很遗憾");
			iv_next.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_repeat:
			Intent repeatIntent = new Intent(this,
					PassThroughItemActivity.class);
			repeatIntent.putExtra("number", number);
			repeatIntent.putExtra("ratingNum", ratingNum);
			startActivity(repeatIntent);
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
			break;
		case R.id.iv_next:
			Intent nextIntent = new Intent(this, PassThroughItemActivity.class);
			nextIntent.putExtra("number", number + 1);
			nextIntent.putExtra("ratingNum", 0f);
			startActivity(nextIntent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.btn_record:

			if (flagPCM == 1) {
				mAudioPlayer.stop();
				btn_record.setText("闯关录音");
				flagPCM = 0;
			} else {
				mAudioPlayer.play();
				btn_record.setText("正在播放");
				flagPCM = 1;
			}
			break;
		}

	}

	public void initAudioPlay() {
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case AudioPlayer.STATE_MSG_ID:
					System.out.println(PcmRelated.showState((Integer) msg.obj));
					if ((Integer) msg.obj == 1) {
						btn_record.setText("闯关录音");
					}
					break;
				}
			}

		};

		mAudioPlayer = new AudioPlayer(mHandler);
		// 获取音频参数
		AudioParam audioParam = PcmRelated.getAudioParam();
		mAudioPlayer.setAudioParam(audioParam);
	}

	private void initPCMData() {

		// 获取音频数据
		filePath = PathConstant.getPCMDataPath() + "/outfile" + number + ".pcm";
		byte[] data = PcmRelated.getPCMData(filePath);
		mAudioPlayer.setDataSource(data);

		// 音频源就绪
		mAudioPlayer.prepare();

		if (data == null) {
			System.out.println(filePath + "：该路径下不存在文件！");
		}

	}

	/*
	 * 手机键盘的操作
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent repeatIntent = new Intent(this,
					PassThroughItemActivity.class);
			repeatIntent.putExtra("number", number);
			repeatIntent.putExtra("ratingNum", ratingNum);
			startActivity(repeatIntent);
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();

		}
		return false;
	};

	@Override
	protected void onDestroy() {
		if (mAudioPlayer.play()) {
			mAudioPlayer.stop();

		}
		super.onDestroy();
	}

}
