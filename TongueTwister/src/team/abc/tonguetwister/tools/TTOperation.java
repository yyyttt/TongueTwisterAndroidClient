package team.abc.tonguetwister.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.bean.TongueTwisterList;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;

public class TTOperation {

	private static List<TongueTwister> tTList;
	public static final String PATH = "raokouling.txt";
	private static final String TAG = "TTOperation";

	static {
		tTList = TongueTwisterList.getTTList();
		if (tTList.size() == 0) {
			FileUtil.readFile(PATH, tTList);
		}
	}

	// 合代码添加
	/*
	 * public TTOperation(Context context) { tTList =
	 * TongueTwisterList.getTTList(); if (tTList.size() == 0) {
	 * FileUtil.readFile(PATH, tTList,context); } }
	 */

	// 随机返回一个绕口令
	public static TongueTwister getRandom() {
		int i = new Random().nextInt(tTList.size());
		return tTList.get(i);
	}

	// 返回所有的绕口令
	public static List<TongueTwister> getAllTT() {

		List<TongueTwister> list = new ArrayList<TongueTwister>();

		for (int i = 0; i < tTList.size(); i++) {
			list.add(tTList.get(i));
		}

		return list;
	}

	// 返回N个绕口令
	public static List<TongueTwister> getSomTT(int num) {
		List<TongueTwister> list = new ArrayList<TongueTwister>();

		if (num > tTList.size()) {
			return null;
		}

		for (int i = 0; i < num; i++) {
			list.add(tTList.get(i));
		}

		return list;
	}

	// 返回前已收藏的所有绕口令
	public static List<TongueTwister> getCollectionTT() {
		List<TongueTwister> list = new ArrayList<TongueTwister>();
		TongueTwister tt = null;

		for (int i = 0; i < tTList.size(); i++) {
			tt = tTList.get(i);
			if (hasCollected(tt)) {
				list.add(tt);
			}
		}

		Log.i(TAG, list.toString());

		return list;

	}

	// 返回指定的一个绕口令
	public static TongueTwister getAppointedOneTT(int num) {
		System.out.println("tTList.size():" + tTList.size());
		if (num > tTList.size()) {
			return null;
		}
		return tTList.get(num);
	}

	// 返回指定区间的绕口令
	public static List<TongueTwister> getAppointedSomeTT(int begin, int end) {
		List<TongueTwister> list = new ArrayList<TongueTwister>();

		if (begin > tTList.size() || end > tTList.size()) {
			return null;
		}

		for (int i = begin; i <= end; i++) {
			list.add(tTList.get(i));
		}

		return list;
	}

	// 随机返回N个绕口令。
	public static List<TongueTwister> getSomeRandom(int num) {
		List<TongueTwister> list = new ArrayList<TongueTwister>();

		for (int i = 0; i < num; i++) {
			list.add(getRandom());
		}

		return list;
	}

	private static boolean hasCollected(TongueTwister tt) {
		
		return TongueTwisterDetailsDb.getDbInstance(
				MyApplication.getMyAppContext()).getSingleCollectState(
				tt.getId());
	}

}
