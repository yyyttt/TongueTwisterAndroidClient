/*
 * 闯关模式
 */

package team.abc.tonguetwister.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.PathConstant;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;
import team.abc.tonguetwister.tools.HanZiToPinYinUtil;
import team.abc.tonguetwister.tools.NetWorkUtil;
import team.abc.tonguetwister.tools.ScoreCountUtil;
import team.abc.tonguetwister.tools.StringSimilarityUtil;
import team.abc.tonguetwister.tools.TTOperation;
import team.abc.tonguetwister.tools.RecognizeRelatedUtil;
import team.abc.tonguetwister.tools.RecordPermissionUtil;
import team.abc.tonguetwister.tools.TimeDifferenceUtil;
import team.abc.tonguetwister.tools.WordCountUtil;
import team.abc.tonguetwister.widget.BuileGestureExt;
import team.abc.tonguetwister.widget.CircleButton;
import team.abc.tonguetwister.widget.CustomProgressDialog;
import team.abc.tonguetwister.widget.ShowMaterialDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONObject;

import com.audioplayer.demo.AudioParam;
import com.audioplayer.demo.AudioPlayer;
import com.audioplayer.demo.PcmRelated;
import com.baidu.speech.VoiceRecognitionService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

public class PassThroughItemActivity extends Activity implements
		RecognitionListener, OnClickListener {
	TextView tv_word, tv_number;
	RatingBar ratingbar;
	private static final int EVENT_ERROR = 11;
	CircleButton btn_start, btn_record, btn_back;
	View speechTips;
	View speechWave;
	private SpeechRecognizer speechRecognizer;
	private long speechEndTime = -1;
	SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 时间具体到毫秒
	long between = 0;
	long[] time_difference = new long[6];
	String begin_time, end_time, wordConvertToPinyin, resultConvertToPinyin;
	int wordNumber = 0;// 绕口令的字数
	float similiarRatio, ratingNum;// 相似度,星星属数目,传值
	private AudioPlayer mAudioPlayer; // 播放器
	private Handler mHandler;
	int flagPCM = 0;
	String filePath;
	int number;
	TongueTwister tongueTwister;
	String wordContent;
	CustomProgressDialog dialog_refresh;
	private GestureDetector gestureDetector;
	public boolean isNextEnabled;
	private static final String TAG = "PassThroughItemActivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pass_through_item);
	
		
		
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			number = bundle.getInt("number");
			ratingNum = bundle.getFloat("ratingNum");
		}
		initAudioPlay();
		initValue(number, ratingNum);

		initRecognize();
		
		if (!RecordPermissionUtil.isHasPermission(PassThroughItemActivity.this)) {
			RecordPermissionUtil.RecordDialog(PassThroughItemActivity.this);
		}

		gestureDetector = new BuileGestureExt(this,
				new BuileGestureExt.OnGestureResult() {
					@Override
					public void onGestureResult(int direction) {
						System.out.println(Integer.toString(direction));
						if (direction == Constant.GESTURE_UP
								|| direction == Constant.GESTURE_LEFT) {
							if (isNextEnabled == true) {

								if (mAudioPlayer.play()) {
									mAudioPlayer.stop();

								}

								if (number == 99) {
									PassThroughItemActivity.this.finish();
								} else {
									number = number + 1;
									ratingNum = TongueTwisterDetailsDb
											.getDbInstance(
													getApplicationContext())
											.getOnePassThrough(number)
											.getRatingNum();
									initValue(number, ratingNum);

								}
							} else {
								Toast.makeText(getApplicationContext(),
										"这一关还没有解锁哦", Toast.LENGTH_LONG).show();
							}
						} else {

							if (mAudioPlayer.play()) {
								mAudioPlayer.stop();
							}

							if (number == 0) {

								PassThroughItemActivity.this.finish();
							} else {
								number = number - 1;
								ratingNum = TongueTwisterDetailsDb
										.getDbInstance(getApplicationContext())
										.getOnePassThrough(number)
										.getRatingNum();
								initValue(number, ratingNum);

							}
						}
					}
				}).Buile();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private void initValue(int numberPass, float ratingNumPass) {

		// 合代码改动
		// tongueTwister = new
		// TTOperation(MyApplication.getMyAppContext()).getAppointedOneTT(numberPass);
		tongueTwister = TTOperation.getAppointedOneTT(numberPass);
		wordContent = tongueTwister.getContent();
		wordNumber = WordCountUtil.wordCount(wordContent);
		wordConvertToPinyin = HanZiToPinYinUtil
				.converterToSpellAll(wordContent);
		initPCMData(numberPass);
		initView(ratingNumPass);
	}

	private void initView(float ratingNumPass) {

		tv_word = (TextView) findViewById(R.id.tv_word);
		tv_word.setText(wordContent);

		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_number.setText("第" + (number + 1) + "关");

		ratingbar = (RatingBar) findViewById(R.id.ratingbar);
		ratingbar.setRating(ratingNumPass);
		btn_start = (CircleButton) findViewById(R.id.btn_start);
		btn_record = (CircleButton) findViewById(R.id.btn_record);
		btn_start.setEnabled(true);
		btn_record.setOnClickListener(this);

		btn_back = (CircleButton) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);

		dialog_refresh = new CustomProgressDialog(PassThroughItemActivity.this,
				"打分中...", R.anim.loading);
		isNextEnable();

	}

	/*
	 * 判断btn_next是否可见
	 */
	private void isNextEnable() {
		if (ratingNum < 3f
				&& TongueTwisterDetailsDb
						.getDbInstance(PassThroughItemActivity.this)
						.getOnePassThrough(number + 1).getIsPassThrough() == 0) {

			isNextEnabled = false;
		} else if (number == 111) {

			isNextEnabled = false;
		} else {

			isNextEnabled = true;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_record:
			btn_start.setEnabled(false);
			byte[] data=initPCMData(number);
			if (data==null) {
				Toast.makeText(PassThroughItemActivity.this, "还没有该录音文件或已删除", Toast.LENGTH_LONG).show();
				
			}else {
				if (flagPCM == 1) {
					btn_record.setImageResource(R.drawable.icon_record_un);
					mAudioPlayer.stop();
					flagPCM = 0;
				} else {
					btn_record.setImageResource(R.drawable.icon_record);
					mAudioPlayer.play();
					flagPCM = 1;
				}	
			}
			
			
			break;

		case R.id.btn_back:
			startActivity(new Intent(this, PassThroughActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
			break;
		default:
			break;
		}
	}

	private void initRecognize() {
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this,
				new ComponentName(this, VoiceRecognitionService.class));
		speechRecognizer.setRecognitionListener(this);

		speechTips = View.inflate(this, R.layout.speech_pop, null);
		speechWave = speechTips.findViewById(R.id.wave);
		speechTips.setVisibility(View.GONE);
		addContentView(speechTips, new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));

		btn_start.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (!NetWorkUtil.isNetworkAvailable(MyApplication
							.getMyAppContext())) {
						dialog_refresh.cancel();  
						ShowMaterialDialog.showMaterialDialog(
								Constant.NO_NETWORK,PassThroughItemActivity.this);
						break;
					}
					
					if (!RecordPermissionUtil.isHasPermission(PassThroughItemActivity.this)) {
						dialog_refresh.cancel();    
						RecordPermissionUtil.RecordDialog(PassThroughItemActivity.this);
						break;
					}
					if (!SpeechRecognizer
							.isRecognitionAvailable(PassThroughItemActivity.this)) {
						Log.i(TAG, "识别不可用……重启中");
						speechRecognizer.destroy();
						initRecognize();
					}

					begin_time = dfs.format(new Date());
					speechTips.setVisibility(View.VISIBLE);
					// speechRecognizer.cancel();
					
					Intent intent = new Intent();
					RecognizeRelatedUtil.bindParams(intent);
					intent.putExtra(Constant.EXTRA_OUTFILE, filePath);//输出文件位置
					intent.putExtra("vad", "touch");
					speechRecognizer.startListening(intent);

					break;
				case MotionEvent.ACTION_UP:
					if (!RecordPermissionUtil.isHasPermission(PassThroughItemActivity.this)||!NetWorkUtil.isNetworkAvailable(MyApplication
							.getMyAppContext())) {
						break;
					}
					end_time = dfs.format(new Date());
					time_difference = TimeDifferenceUtil.timeDifference(
							begin_time, end_time);
					between = time_difference[0];
					speechRecognizer.stopListening();
					speechTips.setVisibility(View.GONE);
					dialog_refresh.show();
					if (between < 1000) {
						dialog_refresh.cancel();    	
					}
					break;
				}
				return false;
			}
		});

	}

	/*
	 * 以下为Recognise相关
	 */
	public void initAudioPlay() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case AudioPlayer.STATE_MSG_ID:
					System.out.println(PcmRelated.showState((Integer) msg.obj));
					if ((Integer) msg.obj == 1) {
						btn_record.setImageResource(R.drawable.icon_record_un);
						btn_start.setEnabled(true);
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

	private byte[] initPCMData(Integer numberPCM) {

		// 获取音频数据
		filePath = PathConstant.getPCMDataPath()+ "/outfile" + numberPCM + ".pcm";
		byte[] data = PcmRelated.getPCMData(filePath);
		mAudioPlayer.setDataSource(data);

		// 音频源就绪
		mAudioPlayer.prepare();

		if (data == null) {
			System.out.println(filePath + "：该路径下不存在文件！");
		}
		return data;

	}

	@Override
	public void onReadyForSpeech(Bundle params) {

	}

	@Override
	public void onBeginningOfSpeech() {

	}

	@Override
	public void onRmsChanged(float rmsdB) {
		final int VTAG = 0xFF00AA01;
		Integer rawHeight = (Integer) speechWave.getTag(VTAG);
		if (rawHeight == null) {
			rawHeight = speechWave.getLayoutParams().height;
			speechWave.setTag(VTAG, rawHeight);
		}

		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) speechWave
				.getLayoutParams();
		params.height = (int) (rawHeight * rmsdB * 0.01);
		params.height = Math.max(params.height, speechWave.getMeasuredWidth());
		speechWave.setLayoutParams(params);

	}

	@Override
	public void onBufferReceived(byte[] buffer) {
	}

	@Override
	public void onEndOfSpeech() {
		Log.i(TAG, ">>>>>>>>>>>>onEndOfSpeech");
		// mAudioPlayer.stop();
	}

	@Override
	public void onError(int error) {
		StringBuilder sb = new StringBuilder();
		switch (error) {
		case SpeechRecognizer.ERROR_AUDIO:
			sb.append("音频问题");
			break;
		case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
			sb.append("没有语音输入");
			break;
		case SpeechRecognizer.ERROR_CLIENT:
			sb.append("其它客户端错误");
			break;
		case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
			sb.append("权限不足");
			break;
		case SpeechRecognizer.ERROR_NETWORK:
			sb.append("网络问题");
			break;
		case SpeechRecognizer.ERROR_NO_MATCH:
			sb.append("没有匹配的识别结果");

			break;
		case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
			sb.append("引擎忙");
			break;
		case SpeechRecognizer.ERROR_SERVER:
			sb.append("服务端错误");
			break;
		case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
			sb.append("连接超时");
			break;
		}
		sb.append(":" + error);
		System.out.println("识别失败：" + sb.toString());
		// 貌似以下没有运行 by zsc
		Log.i(TAG, "recognise error");
		dialog_refresh.cancel();
        if (sb.toString().contains("音频问题")) {
        	speechRecognizer.destroy();
//        	RecordPermissionUtil.RecordDialog(PassThroughItemActivity.this);
        	
	    }else if (sb.toString().contains("引擎忙")){
	    	speechRecognizer.cancel();
		}else{
	    Intent gradeIntent = new Intent(this, PassThroughGradeActivity.class);
	    gradeIntent.putExtra("ratingNum", 0f);
	    gradeIntent.putExtra("number", number);
	    startActivity(gradeIntent);
	    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	    finish();
        }
			
		ratingbar.setRating(0);
		TongueTwisterDetailsDb.getDbInstance(PassThroughItemActivity.this)
				.passthrough_update(number, 0, 1);

	}

	@Override
	public void onResults(Bundle results) {
		long end2finish = System.currentTimeMillis() - speechEndTime;
		ArrayList<String> nbest = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		System.out.println("识别成功："
				+ Arrays.toString(nbest.toArray(new String[nbest.size()])));
		Log.i(TAG,
				"识别成功："
						+ Arrays.toString(nbest.toArray(new String[nbest.size()])));
		String json_res = results.getString("origin_result");
		try {
			System.out.println("origin_result=\n"
					+ new JSONObject(json_res).toString(4));
		} catch (Exception e) {
			System.out
					.println("origin_result=[warning: bad json]\n" + json_res);
		}
		String strEnd2Finish = "";
		if (end2finish < 60 * 1000) {
			strEnd2Finish = "(waited " + end2finish + "ms)";
		}

		resultConvertToPinyin = HanZiToPinYinUtil.converterToSpellAll(nbest
				.get(0) + strEnd2Finish);
		Log.e("识别出的汉字", nbest.get(0) + strEnd2Finish);
		similiarRatio = StringSimilarityUtil.getSimilarityRatio(
				wordConvertToPinyin, resultConvertToPinyin);
		int resultNumber = WordCountUtil
				.wordCount(nbest.get(0) + strEnd2Finish);
		Log.e("识别出的汉字个数", "resultNumber:" + resultNumber + " wordnumber:"
				+ wordNumber);
		ratingNum = ScoreCountUtil.scoreCount(number, (resultNumber * 1000)
				/ between, similiarRatio);
		dialog_refresh.cancel();
		Intent gradeIntent = new Intent(this, PassThroughGradeActivity.class);
		gradeIntent.putExtra("ratingNum", ratingNum);
		gradeIntent.putExtra("number", number);
		startActivity(gradeIntent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		finish();
		ratingbar.setRating(ratingNum);
		isNextEnable();
		TongueTwisterDetailsDb.getDbInstance(PassThroughItemActivity.this)
				.passthrough_update(number, ratingNum, 1);
		if (ratingNum >= 3f
				&& TongueTwisterDetailsDb
						.getDbInstance(getApplicationContext())
						.getOnePassThrough(number + 1).getRatingNum() == 0f) {
			TongueTwisterDetailsDb.getDbInstance(PassThroughItemActivity.this)
					.passthrough_update(number + 1, 0f, 1);

		}

	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		ArrayList<String> nbest = partialResults
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		if (nbest.size() > 0) {
			System.out.println("~临时识别结果："
					+ Arrays.toString(nbest.toArray(new String[0])));
		}

	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		switch (eventType) {
		case EVENT_ERROR:
			String reason = params.get("reason") + "";
			System.out.println("EVENT_ERROR, " + reason);
			break;
		case VoiceRecognitionService.EVENT_ENGINE_SWITCH:
			int type = params.getInt("engine_type");
			System.out.println("*引擎切换至" + (type == 0 ? "在线" : "离线"));
			if (type == 0) {
			} else {
				dialog_refresh.cancel();
			}
			break;
		}
	}

	/*
	 * 手机键盘的操作
	 */
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			startActivity(new Intent(this, PassThroughActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return false;
	};

	@Override
	protected void onDestroy() {
		speechRecognizer.destroy();

		if (mAudioPlayer.play()) {
			mAudioPlayer.stop();

		}

		super.onDestroy();
	}

}
