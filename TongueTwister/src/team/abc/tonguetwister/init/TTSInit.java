package team.abc.tonguetwister.init;

import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.tools.CopyFromAssetsToSdcard;
import android.os.Environment;
import android.util.Log;

import com.baidu.tts.answer.auth.AuthInfo;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

public class TTSInit {
	private static SpeechSynthesizer mSpeechSynthesizer;
	private static String mSampleDirPath;
	private static final String TAG = "TTSInit";
	
	/**
	 * 在线语音初始化
	 */
	public static boolean initialOnlineModeTts() {
		mSpeechSynthesizer = SpeechSynthesizer.getInstance();
		mSpeechSynthesizer.setContext(MyApplication.getMyAppContext());
		// 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
		mSpeechSynthesizer.setApiKey(Constant.ApiKey, Constant.SecretKey);
		// 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
		mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "1");
		// 设置Mix模式的合成策略
		mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE,
				SpeechSynthesizer.MIX_MODE_DEFAULT);
		// 授权检测接口(可以不使用，只是验证授权是否成功)
		AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.ONLINE);
		if (authInfo.isSuccess()) {
			Log.i(TAG, "auth success");
			// 初始化tts
			mSpeechSynthesizer.initTts(TtsMode.ONLINE);
			return true;
		} else {
			String errorMsg = authInfo.getTtsError().getDetailMessage();
			Log.i(TAG, "auth failed errorMsg=" + errorMsg);
			return false;
		}
	}
	
	private static void initialEnv() {
		String sdcardPath = Environment.getExternalStorageDirectory()
				.toString();

		if (mSampleDirPath == null) {
			mSampleDirPath = sdcardPath + Constant.TTS_DIR_NAME;
		}

		CopyFromAssetsToSdcard.makeDir(mSampleDirPath);
		CopyFromAssetsToSdcard.copyFromAssetsToSdcard(MyApplication.getMyAppContext(), false,
				Constant.SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
						+ Constant.SPEECH_FEMALE_MODEL_NAME);
		CopyFromAssetsToSdcard.copyFromAssetsToSdcard(MyApplication.getMyAppContext(), false,
				Constant.SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
						+ Constant.SPEECH_MALE_MODEL_NAME);
		CopyFromAssetsToSdcard.copyFromAssetsToSdcard(MyApplication.getMyAppContext(), false,
				Constant.TEXT_MODEL_NAME, mSampleDirPath + "/"
						+ Constant.TEXT_MODEL_NAME);
	}
	
	
	
	
	/**
	 * 混合模式下TTS初始化
	 */
	public static void initialMixModeTTS(){
		
		initialEnv();
		
		mSpeechSynthesizer = SpeechSynthesizer.getInstance();
		mSpeechSynthesizer.setContext(MyApplication.getMyAppContext());
		// 文本模型文件路径 (离线引擎使用)
		mSpeechSynthesizer.setParam(
				SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath
						+ "/" + Constant.TEXT_MODEL_NAME);
		// 声学模型文件路径 (离线引擎使用)
		mSpeechSynthesizer.setParam(
				SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath
						+ "/" + Constant.SPEECH_MALE_MODEL_NAME);
		// 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
		mSpeechSynthesizer.setAppId(Constant.AppID);
		// 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
		mSpeechSynthesizer.setApiKey(Constant.ApiKey, Constant.SecretKey);
		// 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
		mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "1");
		// 设置Mix模式的合成策略
		mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE,
				SpeechSynthesizer.MIX_MODE_DEFAULT);
		// 授权检测接口(可以不使用，只是验证授权是否成功)
		AuthInfo authInfo = mSpeechSynthesizer.auth(TtsMode.MIX);
		if (authInfo.isSuccess()) {
			Log.i(TAG, "auth success");
		} else {
			String errorMsg = authInfo.getTtsError().getDetailMessage();
			Log.i(TAG, "auth failed errorMsg=" + errorMsg);
		}
		// 初始化tts
		mSpeechSynthesizer.initTts(TtsMode.MIX);
	}
}
