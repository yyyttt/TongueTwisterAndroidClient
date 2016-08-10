/*
 * 评分标准
准确率低于0.1,没有星星
float z = (float) (0.5 * similiarRatio + 0.1 * speed);
 */

package team.abc.tonguetwister.tools;

import android.util.Log;

public class ScoreCountUtil {
	
	public static float pkScoreCount(long speed, float similiarRatio) {
		
		if (similiarRatio >= 0.5) { 
			float z = (float) (0.5 * similiarRatio + 0.1 * speed);
			if (z >= 0.6) {
				return 1f;
			} else {
				return 0;
			} 

		}else{
			return 0;
		}
		

	}

	public static float scoreCount(int number, long speed, float similiarRatio) {
		// 20160715 yt
		Log.e("相似度：", similiarRatio+"");
		Log.e("语速：", speed+"");
		if (similiarRatio <= 0.1) {
			return 0;
		} else if (similiarRatio >= 0.5) {
			float z = (float) (0.5 * similiarRatio + 0.1 * speed);
			if (z >= 0.9) {
				return 5f;
			} else if (z >= 0.75 && z < 0.9) {
				return 4f;
			} else if (z >= 0.6 && z < 0.75) {
				return 3f;
			} else if (z >= 0.5 && z < 0.6) {
				return 2f;
			} else {
				return 1f;
			}

		}
		return 1f;

		/*
		 * float z = (float) (0.5 * similiarRatio + 0.1 * speed); //
		 * if(number<50){ if (z >= 0.9) { return 5f; } else if (z >= 0.7 && z <
		 * 0.9) { return 4f; } else if (z >= 0.6 && z < 0.7) { return 3f; } else
		 * if(z >= 0.5 && z < 0.6){ return 2f; }else{ return 1f; }
		 */
		// }else{
		// if (z == 1) {
		// return 5f;
		// } else if (z >= 0.9 && z < 1) {
		// return 4f;
		// } else if (z >= 0.8 && z < 0.9) {
		// return 3f;
		// } else if (z >= 0.7 && z < 0.8) {
		// return 2f;
		// } else {
		// return 1f;
		// }
		// }

	}

}
