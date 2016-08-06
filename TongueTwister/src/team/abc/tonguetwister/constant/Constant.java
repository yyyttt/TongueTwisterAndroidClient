package team.abc.tonguetwister.constant;
import android.app.Activity;
import android.util.DisplayMetrics;


public class Constant {
	// Recognise
	public static final String RECOGNISE_DIR_NAME ="/TongueTwister/Recognise";
	public static final String EXTRA_KEY = "key";
	public static final String EXTRA_SECRET = "secret";
	public static final String EXTRA_SAMPLE = "sample";
	public static final String EXTRA_SOUND_START = "sound_start";
	public static final String EXTRA_SOUND_END = "sound_end";
	public static final String EXTRA_SOUND_SUCCESS = "sound_success";
	public static final String EXTRA_SOUND_ERROR = "sound_error";
	public static final String EXTRA_SOUND_CANCEL = "sound_cancel";
	public static final String EXTRA_INFILE = "infile";
	public static final String EXTRA_OUTFILE = "outfile";

	public static final String EXTRA_LANGUAGE = "language";
	public static final String EXTRA_NLU = "nlu";
	public static final String EXTRA_VAD = "vad";
	public static final String EXTRA_PROP = "prop";

	public static final String EXTRA_OFFLINE_ASR_BASE_FILE_PATH = "asr-base-file-path";
	public static final String EXTRA_OFFLINE_LM_RES_FILE_PATH = "lm-res-file-path";
	

	public static final int SAMPLE_8K = 8000;
	public static final int SAMPLE_16K = 16000;

	public static final String VAD_SEARCH = "search";
	public static final String VAD_INPUT = "input";

	// TTS
	public static final String TTS_DIR_NAME = "/TongueTwister/TTS";
	
	public static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
	public static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
	public static final String LICENSE_FILE_NAME = "temp_license";
	public static final String TEXT_MODEL_NAME = "bd_etts_text.dat";

	// apikey,secretkey,AppiD
	public static final String AppID = "8155626";

	public static final String ApiKey = "G6yuH2e5mGqYb1pN6fUNiXXa";

	public static final String SecretKey = "987c87b2629763223ea4ff4bafc166fd";

	// 闯关界面
	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;

	public static int curentPage = 0;
	public static int countPages = 0;
	
	public static void init(Activity context) {
		if (screenDensity == 0 || screenWidth == 0 || screenHeight == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			Constant.screenDensity = dm.density;
			Constant.screenHeight = dm.heightPixels;
			Constant.screenWidth = dm.widthPixels;
		}
		curentPage = 0;
		countPages = 0;
	}
	//手势动作
	public static final int GESTURE_UP = 0;
	public static final int GESTURE_DOWN = 1;
	public static final int GESTURE_LEFT = 2;
	public static final int GESTURE_RIGHT = 3;
	//版本更新
	public static final String APK_DOWNLOAD_URL = "url";
	public static final String APK_UPDATE_CONTENT = "updateMessage";
	public static final String APK_VERSION_CODE = "versionCode";


	public static final int TYPE_DIALOG = 1;

	public static final String TAG = "UpdateChecker";
    //网络状况
	public static final String NO_NETWORK = "未连接网络";
	public static final String DISABLE_NETWORK = "网络不可用";
	public static final String PREPARE_SUCCESS = "success";
	
	//录音
	public static final String RECORD_DENIED = "检测到录音失败,请尝试按以下路径开启:"
			+ "方法一,360卫士——软件管理——权限管理——吧嗒绕口令——使用话筒录音/通话录音——允许;"
			+ "方法二,手机管家——权限管理——应用程序——吧嗒绕口令——录音——允许;"
			+ "方法三,安全中心——授权管理——应用权限管理——吧嗒绕口令——录音——允许;"
			+ "方法四,在设置中找到应用权限管理,设置录音权限为允许";
	//按钮声明
	public static final String BUTTON_EXPLAIN = "需要长按说话哦";
}
