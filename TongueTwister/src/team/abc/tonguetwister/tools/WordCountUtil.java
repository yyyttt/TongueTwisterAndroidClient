package team.abc.tonguetwister.tools;


public class WordCountUtil {
	/*
	 * 统计所给语句中汉语的字数
	 */
	public static int wordCount(String word) {
		char[] t1 = null;
		t1 = word.toCharArray();
		int t0 = t1.length;
		int count = 0;
		for (int i = 0; i < t0; i++) {
			if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
				count++;

			}
		}
		return count;

	}


}
