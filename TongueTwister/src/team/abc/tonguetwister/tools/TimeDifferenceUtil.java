package team.abc.tonguetwister.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeDifferenceUtil {
	
	/*
	 * 时间差
	 */
	public static long[] timeDifference(String startTime, String endTime) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long between = 0;
		long[] time = new long[6];

		 System.out.println(dfs.format(new Date()));// 当前时间

		try {
			java.util.Date begin = dfs.parse(startTime);
			java.util.Date end = dfs.parse(endTime);
			between = (end.getTime() - begin.getTime());// 得到两者相差的毫秒数
			System.out.println(between);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		System.out.println("具体的相差时间:" + day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒");
		time[0] = between;
		time[1] = day;
		time[2] = hour;
		time[3] = min;
		time[4] = s;
		time[5] = ms;
		return time;
	}

}
