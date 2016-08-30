package team.abc.tonguetwister.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanZiToPinYinUtil {
	
	private static final String TAG = "HanyuToPinyin";
	/**
	 * 一个或多个汉字
	 */
	private static final String CHINESE_WORD_REGEX = "[\u4E00-\u9FA5]+";

	private static HanyuPinyinOutputFormat mDefaultFormat = new HanyuPinyinOutputFormat();

	private static HanyuPinyinOutputFormat getInstance() {
		if (mDefaultFormat == null) {
			mDefaultFormat = new HanyuPinyinOutputFormat();
		}

		return mDefaultFormat;
	}

	// 将汉语转换为拼音，只输出拼音的首字母
	public static String converterToFirstSpell(String chines) {
		if (chines == null) {
			Log.e(TAG, "string value = null, return!");
			return "";
		}
		// 去除字符串中的标点符号 yytt
		chines = removePunctuation(chines);

		System.out.println(chines);
		String pinyinName = "";

		try {
			char[] nameChar = chines.toCharArray();

			getInstance().setCaseType(HanyuPinyinCaseType.LOWERCASE);
			getInstance().setToneType(HanyuPinyinToneType.WITHOUT_TONE);

			for (int i = 0; i < nameChar.length; i++) {
				if (nameChar[i] > 128) {
					try {
						pinyinName += PinyinHelper.toHanyuPinyinStringArray(
								nameChar[i], getInstance())[0].charAt(0);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						Log.e(TAG, e + "");
					}
				} else {
					pinyinName += nameChar[i];
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e + "");
			pinyinName = "";
		}
		return pinyinName;
	}

	// 将输入的汉语转换为拼音，输出所有的拼音
	public static String converterToSpellAll(String chines) {

		if (chines == null) {
			Log.e(TAG, "string value = null, return!");
			return "";
		}

		// 去除字符串中的标点符号 yytt
		chines = removePunctuation(chines);

		String pinyinName = "";
		try {
			char[] nameChar = chines.toCharArray();

			getInstance().setCaseType(HanyuPinyinCaseType.LOWERCASE);
			getInstance().setToneType(HanyuPinyinToneType.WITHOUT_TONE);

			for (int i = 0; i < nameChar.length; i++) {
				if (nameChar[i] > 128) {
					try {
						pinyinName += PinyinHelper.toHanyuPinyinStringArray(
								nameChar[i], getInstance())[0];
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						Log.e(TAG, e + "");
					}
				} else {
					pinyinName += nameChar[i];
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e + "");
			pinyinName = "";
		}

		return pinyinName;
	}

	/**
	 * @author YYTT
	 * @param 
	 * 		s 带有标点的字符串。
	 * @return 
	 * 		只含有汉字的字符串。
	 */
	private static String removePunctuation(String s) {
		StringBuffer stringBuffer = new StringBuffer();
		Matcher matcherString = Pattern.compile(CHINESE_WORD_REGEX).matcher(s);
		while (matcherString.find()) {
			stringBuffer.append(matcherString.group());
		}
		s = stringBuffer.toString();
		return s;
	}

}
