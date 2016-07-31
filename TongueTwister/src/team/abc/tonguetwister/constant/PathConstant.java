package team.abc.tonguetwister.constant;

import android.os.Environment;

public class PathConstant {
	
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/TongueTwisterData";
	
	public static final String PCM_DATA_PATH = DATA_PATH + "/PCMData";
}
