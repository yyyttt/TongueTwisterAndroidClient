/*
 * 挑战模式
 */

package team.abc.tonguetwister.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
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
import team.abc.tonguetwister.tools.HanZiToPinYinUtil;
import team.abc.tonguetwister.tools.NetWorkUtil;
import team.abc.tonguetwister.tools.ScoreCountUtil;
import team.abc.tonguetwister.tools.StringSimilarityUtil;
import team.abc.tonguetwister.tools.TTOperation;
import team.abc.tonguetwister.tools.TTSRelatedUtil;
import team.abc.tonguetwister.tools.TimeDifferenceUtil;
import team.abc.tonguetwister.tools.WordCountUtil;
import team.abc.tonguetwister.widget.CircleButton;
import team.abc.tonguetwister.widget.CustomProgressDialog;
import team.abc.tonguetwister.widget.ShowMaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONObject;

import com.baidu.speech.VoiceRecognitionService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;

public class PkActivity extends Activity implements
		RecognitionListener {
	TextView tv_word, tv_number,tv_countdown;
	RatingBar ratingbar;
	private static final int EVENT_ERROR = 11;
	CircleButton btn_start;
	View speechTips;
	View speechWave;
	private SpeechRecognizer speechRecognizer;
	private long speechEndTime = -1;
	SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 时间具体到毫秒
	long between = 0;
	long[] time_difference = new long[6];
	String begin_time, end_time, wordConvertToPinyin, resultConvertToPinyin;
	int wordNumber,wordTime;// 绕口令的字数
	float similiarRatio, ratingNum;// 相似度,星星属数目
    private long millisInFuture ;//倒计时
	String filePath;
	int number;
	TongueTwister tongueTwister;
	String wordContent;
	CustomProgressDialog dialog_refresh;
	private static final String TAG = "PKActivity";
    private long countDownInterval = 1000;//每隔一秒
    private MyCountDownTimer myCountDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pk);
		
		ratingbar = (RatingBar) findViewById(R.id.pk_ratingbar);
		ratingbar.setRating(5);
        
		dialog_refresh = new CustomProgressDialog(PkActivity.this,
				"打分中...", R.anim.loading);
		initRecognize();
		initValue(0);

		

	}

	private void initValue(int numberPass) {

		tongueTwister = TTOperation.getAppointedOneTT(numberPass);
		wordContent = tongueTwister.getContent();
		wordNumber = WordCountUtil.wordCount(wordContent);
		wordTime=(wordNumber/4)+1;
		millisInFuture=wordTime*1000;
		wordConvertToPinyin = HanZiToPinYinUtil
				.converterToSpellAll(wordContent);
		filePath = PathConstant.getPCMDataPath()+ "/outfile" + number + ".pcm";
		
		initView();
	}

	private void initView() {

		tv_word = (TextView) findViewById(R.id.tv_word);
		tv_word.setText(wordContent);

		tv_number = (TextView) findViewById(R.id.tv_number);
		tv_number.setText("第" + (number + 1) + "战");
		
		tv_countdown=(TextView) findViewById(R.id.tv_countdown);
		tv_countdown.setText(wordTime+" s");
		
		btn_start = (CircleButton) findViewById(R.id.btn_start);
		btn_start.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (!NetWorkUtil.isNetworkAvailable(MyApplication
							.getMyAppContext())) {
						dialog_refresh.cancel();    	
						ShowMaterialDialog.showMaterialDialog(
								Constant.NO_NETWORK,PkActivity.this);
						break;
					}
					if (!SpeechRecognizer
							.isRecognitionAvailable(PkActivity.this)) {
						Log.i(TAG, "识别不可用……重启中");
						speechRecognizer.destroy();
						initRecognize();
					}

					begin_time = dfs.format(new Date());
					speechTips.setVisibility(View.VISIBLE);
					// speechRecognizer.cancel();
					
					Intent intent = new Intent();
					TTSRelatedUtil.bindParams(intent);
					intent.putExtra(Constant.EXTRA_OUTFILE, filePath);//输出文件位置
					intent.putExtra("vad", "touch");
					speechRecognizer.startListening(intent);
					
					myCountDownTimer = new MyCountDownTimer(millisInFuture, countDownInterval);
				    myCountDownTimer.start();

					break;
				case MotionEvent.ACTION_UP:
				    myCountDownTimer.cancel();
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
	}

	/*
	 * 以下为Recognise相关
	 */
	
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
        	ShowMaterialDialog.showMaterialDialog(
					Constant.RECORD_DENIED,PkActivity.this);
        	
	    }else if (sb.toString().contains("引擎忙")){
	    	speechRecognizer.cancel();
		}else{
	    Toast.makeText(PkActivity.this, "挑战失败", Toast.LENGTH_LONG).show();
	    if (ratingbar.getRating()==1f) {
        	ratingbar.setRating(0);
			finish();
		}
        ratingbar.setRating(ratingbar.getRating()-1f);
        number=number+1;
		initValue(number);
        }
       
        
		

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
		if (ratingNum>=3f) {
			Toast.makeText(PkActivity.this, "挑战成功", Toast.LENGTH_LONG).show();	
			ratingbar.setRating(ratingbar.getRating()+1f);
		}else{
			Toast.makeText(PkActivity.this, "挑战失败", Toast.LENGTH_LONG).show();		
           if (ratingbar.getRating()==1f) {
        	   ratingbar.setRating(0);
        	   finish();
			}else {
				ratingbar.setRating(ratingbar.getRating()-1f);
				
			}
		}
		number=number+1;
		initValue(number);
		
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

//			startActivity(new Intent(this, MainActivity.class));
//			overridePendingTransition(R.anim.push_right_in,
//					R.anim.push_right_out);
			finish();
		}
		return false;
	};

	@Override
	protected void onDestroy() {
		speechRecognizer.destroy();
		 if (myCountDownTimer != null) {
	            myCountDownTimer.cancel();
	        }
		super.onDestroy();
	}
	
	 public class MyCountDownTimer extends CountDownTimer {

	        /**
	         * @param millisInFuture    总共持续的时间
	         * @param countDownInterval 倒计时的时间间隔
	         */
	        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
	            super(millisInFuture, countDownInterval);
	        }

	        /**
	         * @param millisUntilFinished 还剩下的时间
	         */
	        @Override
	        public void onTick(long millisUntilFinished) {
	        	tv_countdown.setText(millisUntilFinished / countDownInterval + "s");
	        	}

	        /**
	         * 倒计时结束时候回调
	         */
	        @Override
	        public void onFinish() { 
	        	speechRecognizer.stopListening();
	        	  if (ratingbar.getRating()==1f) {
	           	   ratingbar.setRating(0);
	           	   finish();
	   			}else {
	   				ratingbar.setRating(ratingbar.getRating()-1f);
	   				number=number+1;
	   				initValue(number);
	   				
	   			}
	        }
	    }


}
