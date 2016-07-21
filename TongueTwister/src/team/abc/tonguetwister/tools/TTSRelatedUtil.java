package team.abc.tonguetwister.tools;

import android.content.Intent;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.Constant;

public class TTSRelatedUtil {
	public static void bindParams(Intent intent,int number) {
		intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
		intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
		intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
		intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
		intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);

		intent.putExtra(Constant.EXTRA_OUTFILE, "sdcard/outfile" + number + ".pcm");

		intent.putExtra(Constant.EXTRA_LANGUAGE, "cmn-Hans-CN");
		intent.putExtra(Constant.EXTRA_PROP, 20000);
		intent.putExtra(Constant.EXTRA_SAMPLE, Constant.SAMPLE_16K);

		// offline asr
		{
			intent.putExtra(Constant.EXTRA_OFFLINE_ASR_BASE_FILE_PATH, "sdcard/s_1");
			intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "sdcard/s_2_InputMethod");
		}
	}

}
