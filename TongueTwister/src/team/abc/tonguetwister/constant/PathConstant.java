package team.abc.tonguetwister.constant;

import java.io.File;

import android.os.Environment;

public class PathConstant {
	
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/TongueTwisterData";
	
	public static final String PCM_DATA_PATH = DATA_PATH + "/PCMData";
	
	
	public static String getPCMDataPath() {

		File file = new File(PathConstant.PCM_DATA_PATH);

		// 创建文件夹及父文件夹。
		if (!file.exists()) {
			file.mkdirs();
		}

		return file.getAbsolutePath();
	}
}
