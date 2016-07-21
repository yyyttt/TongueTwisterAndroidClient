package team.abc.tonguetwister.activity;

import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.bean.TongueTwisterList;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.StudyMode;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;
import team.abc.tonguetwister.tools.BuilderGestureExt;
import team.abc.tonguetwister.tools.CopyFromAssetsToSdcard;
import team.abc.tonguetwister.tools.NetWorkUtil;
import team.abc.tonguetwister.tools.TTOperation;
import team.abc.tonguetwister.widget.CircleButton;
import team.abc.tonguetwister.widget.RangeSliderView;
import team.abc.tonguetwister.widget.SegmentControlView;
import team.abc.tonguetwister.widget.ShowMaterialDialog;

import com.baidu.tts.answer.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import android.app.Activity;
import android.content.Intent;

/**
 * 
 * @author zsc
 * 
 */

public class StudyActivity extends Activity implements OnClickListener {

	private TextView tvTongueTwister, tvTongueTwisterTitle, tvSpeekSpeedIndex;
	private ImageView ivNextTT, ivFormerTT;
	private CircleButton cbtnStart, cbtnStop, cbtnCollection;
	private ProgressBar proBar;
	private SegmentControlView scGender;
	private RangeSliderView rsvSpeekSpeed;

	private GestureDetector gestureDetector;// 手势识别

	private Boolean ttsIsStart = false;
	private Boolean ttsIsPause = false;
	private Boolean isCollection = false;
	private Boolean isRandom = false;
	private int tonguetwisterIndex;
	private int tonguetwisterID;
	private List<TongueTwister> tonguetwisterList;
	private TongueTwister currentTwister;
	private int maxIndex;

	private static final String TAG = "StudyActivity";
	private static final int SPEECN_FINISH = 1;
	private static final int SPEECH_PROCESS = 2;
	private SpeechSynthesizer mSpeechSynthesizer;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SPEECN_FINISH:

				Log.i(TAG, "Speech has finish");
				mSpeechSynthesizer.stop();
				cbtnStart.setImageResource(R.drawable.icon_begin);
				ttsIsStart = false;
				ttsIsPause = false;
				proBar.setProgress(0);

				break;

			case SPEECH_PROCESS:

				proBar.setProgress((Integer) msg.obj);

				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_study);
		/*
		 * String randomMode = getIntent().getStringExtra(
		 * StudyMode.class.getName());
		 * 
		 * if(StudyMode.mode == StudyMode.MODE_COLLECTION){ tonguetwisterList =
		 * TTOperation.getCollectionTT(); }else{ tonguetwisterList =
		 * TTOperation.getAllTT(); }
		 */

		switch (StudyMode.mode) {
		case StudyMode.MODE_LIST:
			tonguetwisterList = TTOperation.getAllTT();
			tonguetwisterID = getIntent().getIntExtra("tonguetwisterID", 0);
			maxIndex = tonguetwisterList.size() - 1;// 得到最大索引
			break;

		case StudyMode.MODE_COLLECTION:
			tonguetwisterList = TTOperation.getCollectionTT();
			tonguetwisterID = getIntent().getIntExtra("tonguetwisterID", 0);
			maxIndex = tonguetwisterList.size() - 1;// 得到最大索引

			break;
		case StudyMode.MODE_RANDOM:
			isRandom = true;
			break;

		default:

			break;
		}

		/*
		 * if (randomMode == null) { tonguetwisterID =
		 * getIntent().getIntExtra("tonguetwisterID", 0); maxIndex =
		 * tonguetwisterList.size() - 1;// 得到最大索引 } else { isRandom = true; }
		 */

		initView();

		mSpeechSynthesizer = SpeechSynthesizer.getInstance();
		mSpeechSynthesizer.setContext(this);
		if (!isRandom) {

			initTongueTwister(tonguetwisterID);
		} else {
			deployRandomTonueTwister();
		}

	}

	private void initTongueTwister(int tonguetwisterID) {
		Log.i(TAG, "tonguetwisterID>>>>>>>>>" + tonguetwisterID);
		tonguetwisterIndex = tonguetwisterList.indexOf(new TongueTwister(
				tonguetwisterID));
		Log.i(TAG, "tonguetwisterIndex>>>>>>>>>" + tonguetwisterIndex);
		deployTongueTwister(tonguetwisterIndex);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		mSpeechSynthesizer
				.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {

					@Override
					public void onSynthesizeStart(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSynthesizeFinish(String arg0) {
						// TODO Auto-generated method stub
						Log.i(TAG, "onSynthesizeFinish");

					}

					@Override
					public void onSynthesizeDataArrived(String arg0,
							byte[] arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSpeechStart(String arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSpeechProgressChanged(String arg0, int arg1) {
						// TODO Auto-generated method stub
						Log.i(TAG, "onSpeechProgreeChanged" + arg1);
						Message msg = new Message();
						msg.obj = arg1;
						msg.what = SPEECH_PROCESS;
						handler.sendMessage(msg);
					}

					@Override
					public void onSpeechFinish(String arg0) {
						// 当播放结束时重置
						Log.i(TAG, "onSpeechFinish");
						finishSpeek();

					}

					@Override
					public void onError(String arg0, SpeechError arg1) {
						// TODO Auto-generated method stub

					}
				});

		// 男女声音选择。
		scGender.setOnSegmentControlClickListener(new SegmentControlView.OnSegmentControlClickListener() {

			@Override
			public void onSegmentControlClick(int index) {

				Log.i(TAG, "speed index = " + index);
				// 男index为0 ， 女index为1。参数设定是0为女声，1为男声。
				mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,
						(1 - index) + "");
				finishSpeek();

			}
		});

		// 语速控制。
		rsvSpeekSpeed.setOnSlideListener(new RangeSliderView.OnSlideListener() {

			@Override
			public void onSlide(int index) {

				// 语速范围0-9
				Log.i(TAG, "speed index = " + index);
				mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED,
						index * 2 + 1 + "");
				tvSpeekSpeedIndex.setText((index + 1) + "X");
				finishSpeek();

			}
		});

		// 手势控制，用于切换上一个和下一个绕口令
		gestureDetector = new BuilderGestureExt(this,
				new BuilderGestureExt.OnGestureResult() {

					@Override
					public void onGestureResult(int direction) {

						switch (direction) {

						case BuilderGestureExt.GESTURE_RIGHT:
							formerTongueTwister();
							break;

						case BuilderGestureExt.GESTURE_LEFT:
							nextTongueTwister();
							break;

						default:
							break;
						}

					}
				}).build();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mSpeechSynthesizer.stop();
		super.onDestroy();
	}

	private void initView() {

		tvTongueTwister = (TextView) findViewById(R.id.word);
		tvTongueTwisterTitle = (TextView) findViewById(R.id.title);
		tvSpeekSpeedIndex = (TextView) findViewById(R.id.tv_speek_speed_index);

		cbtnStart = (CircleButton) findViewById(R.id.cbtn_start);
		cbtnStop = (CircleButton) findViewById(R.id.cbtn_stop);
		cbtnCollection = (CircleButton) findViewById(R.id.cbtn_collection);
		cbtnStart.setOnClickListener(this);
		cbtnStop.setOnClickListener(this);
		cbtnCollection.setOnClickListener(this);

		proBar = (ProgressBar) findViewById(R.id.proBar);

		scGender = (SegmentControlView) findViewById(R.id.sc_gender);

		rsvSpeekSpeed = (RangeSliderView) findViewById(R.id.rsv_speek_speed);
		rsvSpeekSpeed.setInitialIndex(2);// 总共5个点，调至中间

		ivFormerTT = (ImageView) findViewById(R.id.iv_former_tt);
		ivNextTT = (ImageView) findViewById(R.id.iv_next_tt);
		ivFormerTT.setOnClickListener(this);
		ivNextTT.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.cbtn_start:
			if (ttsIsStart) {
				if (ttsIsPause) {
					mSpeechSynthesizer.resume();
					cbtnStart.setImageResource(R.drawable.icon_pause);
					ttsIsPause = false;
				} else {
					mSpeechSynthesizer.pause();
					cbtnStart.setImageResource(R.drawable.icon_begin);
					ttsIsPause = true;
				}
			} else {
				
				//播放的时候判断是否有网络。
				if (!NetWorkUtil
						.isNetworkAvailable(MyApplication.getMyAppContext())) {
					ShowMaterialDialog.showMaterialDialog(Constant.NO_NETWORK, StudyActivity.this);
					break;
				}
				
				int result = this.mSpeechSynthesizer.speak(tvTongueTwister
						.getText().toString());
				if (result < 0) {
					System.out
							.println("error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
				}

				cbtnStart.setImageResource(R.drawable.icon_pause);
				ttsIsStart = true;

			}

			break;
		case R.id.cbtn_stop:

			finishSpeek();

			break;

		case R.id.cbtn_collection:

			changeCollection();

			break;

		case R.id.iv_former_tt:

			formerTongueTwister();

			break;

		case R.id.iv_next_tt:

			nextTongueTwister();

			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (StudyMode.mode != StudyMode.MODE_RANDOM) {
			finish();
			startActivity(new Intent(this, StudyTTChooseActivity.class));
		} else {
			super.onBackPressed();
		}
	}

	private void finishSpeek() {
		Message msg = new Message();
		msg.what = SPEECN_FINISH;
		handler.sendMessage(msg);
	}

	private void formerTongueTwister() {

		if (isRandom) {
			deployRandomTonueTwister();
		} else {

			if (tonguetwisterIndex > 0) {
				deployTongueTwister(--tonguetwisterIndex);
			}
		}

	}

	private void nextTongueTwister() {

		if (isRandom) {
			deployRandomTonueTwister();
		} else {
			if (tonguetwisterIndex < maxIndex) {
				deployTongueTwister(++tonguetwisterIndex);
			}
		}

	}

	private void deployTongueTwister(int index) {

		if (index == 0) {
			ivFormerTT.setVisibility(View.GONE);
		} else if (index == maxIndex) {
			ivNextTT.setVisibility(View.GONE);
		} else {
			ivFormerTT.setVisibility(View.VISIBLE);
			ivNextTT.setVisibility(View.VISIBLE);
		}

		currentTwister = tonguetwisterList.get(index);

		isCollection = TongueTwisterDetailsDb.getDbInstance(
				MyApplication.getMyAppContext()).getSingleCollectState(currentTwister.getId());
		//isCollection = currentTwister.getCollectionStatus();

		// 部署收藏状态
		if (isCollection) {
			cbtnCollection.setImageResource(R.drawable.icon_collection);
		} else {
			cbtnCollection.setImageResource(R.drawable.icon_collection_not);
		}

		finishSpeek();
		tvTongueTwister.setText(tonguetwisterList.get(index).getContent());
		tvTongueTwisterTitle.setText(tonguetwisterList.get(index).getTitle());
		proBar.setMax(tvTongueTwister.getText().length());
	}

	private void deployRandomTonueTwister() {

		currentTwister = TTOperation.getRandom();

		isCollection = TongueTwisterDetailsDb.getDbInstance(
				MyApplication.getMyAppContext()).getSingleCollectState(currentTwister.getId());
		
		//isCollection = currentTwister.getCollectionStatus();

		// 部署收藏状态
		if (isCollection) {
			cbtnCollection.setImageResource(R.drawable.icon_collection);
		} else {
			cbtnCollection.setImageResource(R.drawable.icon_collection_not);
		}

		finishSpeek();
		tvTongueTwister.setText(currentTwister.getContent());
		tvTongueTwisterTitle.setText(currentTwister.getTitle());
		proBar.setMax(tvTongueTwister.getText().length());

	}

	private void changeCollection() {

		// 反转收藏状态
		isCollection = !isCollection;

		if (isCollection) {
			cbtnCollection.setImageResource(R.drawable.icon_collection);
			Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();

		} else {
			cbtnCollection.setImageResource(R.drawable.icon_collection_not);
			Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();

		}
		
		TongueTwisterDetailsDb.getDbInstance(
				MyApplication.getMyAppContext()).collect_update(currentTwister.getId(), isCollection);
		
		//currentTwister.setCollectionStatus(isCollection);
	}

}
