package team.abc.tonguetwister.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import team.abc.tonguetwister.bean.TongueTwisterDetails;

public class TongueTwisterDetailsDb extends SQLiteOpenHelper {

	private static final int version = 1;// 版本号
	private SQLiteDatabase db;
	private static TongueTwisterDetailsDb DbHelper;

	private static final String TongueTwisterDetails_TABLE = "TongueTwisterDetailsTable";// table
	// name

	private static final String _ID = "_id";// table key
	private static final String number = "number";
	private static final String ratingNum = "ratingNum";
	private static final String isPassThrough = "isPassThrough";
	private static final String isCollect = "isCollect";

	public static TongueTwisterDetailsDb getDbInstance(Context context) {
		if (DbHelper == null) {
			DbHelper = new TongueTwisterDetailsDb(context);
		}
		return DbHelper;
	}

	public TongueTwisterDetailsDb(Context context) {
		super(context, TongueTwisterDetails_TABLE, null, version);
		try {
			this.db = this.getWritableDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 参数依次说明:上下文环境（例如一个Activity）；数据库名称；一个可选的游标工厂（通常是null）；一个正在使用的数据库版本。
	 */
	public TongueTwisterDetailsDb(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/*
	 * 数据库第一次创建时onCreate方法会被调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + TongueTwisterDetails_TABLE
				+ "(" + _ID + " integer primary key autoincrement," + number
				+ " Integer," + ratingNum + " float," + isPassThrough
				+ " Integer," + isCollect + " Integer" + ")");
	}

	/*
	 * 如果Version的值更改，系统发现数据库版本不同，会调用onUpgrade
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TongueTwisterDetails_TABLE);// 删除旧表
		onCreate(db);// 创建新表
	}

	/**
	 * 打开数据库
	 */
	public void openDB() {
		if (db != null && !db.isOpen()) {
			db = this.getWritableDatabase();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (db != null) {
			db.close();
		}
		close();

	}

	/**
	 * 添加多行
	 */
	public List<TongueTwisterDetails> passThroughAddMore() {
		List<TongueTwisterDetails> pList = new ArrayList<TongueTwisterDetails>();
		TongueTwisterDetails tongueTwisterDetails = new TongueTwisterDetails();
		ContentValues values = new ContentValues();
		if (!tableIsExist(TongueTwisterDetails_TABLE)) {
			return null;
		}
		if (db == null) {
			db = getWritableDatabase();
		}

		db.beginTransaction();

		try {
			for (int i = 0; i < 113; i++) {
				if (i == 0) {
					values.put(isPassThrough, 1);
					tongueTwisterDetails.setIsPassThrough(1);

				} else {
					values.put(isPassThrough, 0);
					tongueTwisterDetails.setIsPassThrough(0);
				}
				values.put(number, i);
				values.put(ratingNum, 0f);
				values.put(isCollect, 0);

				tongueTwisterDetails.setIsCollect(0);
				tongueTwisterDetails.setNumber(i);
				tongueTwisterDetails.setRatingNum(0f);

				pList.add(tongueTwisterDetails);

				// 插入
				db.insert(TongueTwisterDetails_TABLE, null, values);
			}
			// 设置事务标志为成功，当结束事务时就会提交事务
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 结束事务
			db.endTransaction();
		}

		return pList;

	}

	/**
	 * 添加单行
	 */
	// public long pass_through_add(Integer number_add, float ratingnum_add,
	// Integer isoperation_add) {
	// long raw = 0;
	// if (!tableIsExist(TongueTwisterDetails_TABLE)) {
	// return raw;
	// }
	// if (db == null) {
	// db = getWritableDatabase();
	// }
	// ContentValues values = new ContentValues();
	//
	// values.put(number, number_add);
	// values.put(ratingNum, ratingnum_add);
	// values.put(isPassThrough, isoperation_add);
	// // values.put(isCollect, 0);
	//
	// // 插入
	// raw = db.insert(TongueTwisterDetails_TABLE, null, values);
	//
	// return raw;
	//
	// }

	/**
	 * 更新通关某一行
	 */
	public long passthrough_update(Integer number_update,
			float ratingNum_update, Integer isPassthrough_update) {
		long raw = 0;
		if (!tableIsExist(TongueTwisterDetails_TABLE)) {
			return raw;
		}
		if (db == null) {
			db = getWritableDatabase();
		}
		Cursor c = db.rawQuery("select * from " + TongueTwisterDetails_TABLE,
				null);
		raw = c.getCount();
		if (raw > 0) {
			ContentValues values = new ContentValues();

			values.put(ratingNum, ratingNum_update);
			values.put(isPassThrough, isPassthrough_update);
			String where = number + "=" + number_update;
			String whereArgs[] = null;
			db.update(TongueTwisterDetails_TABLE, values, where, whereArgs);
		}
		c.close();
		return raw;

	}

	/**
	 * 更新收藏某一行,传入的数据isCollect_update=0,表明取消收藏；isCollect_update=1，表明收藏成功
	 */
	public long collect_update(Integer number_update, boolean isCollect_update) {
		long raw = 0;
		if (!tableIsExist(TongueTwisterDetails_TABLE)) {
			return raw;
		}
		if (db == null) {
			db = getWritableDatabase();
		}
		Cursor c = db.rawQuery("select * from " + TongueTwisterDetails_TABLE,
				null);
		raw = c.getCount();
		if (raw > 0) {
			ContentValues values = new ContentValues();
			
			if (isCollect_update) {
				values.put(isCollect, 1);
			} else {
				values.put(isCollect, 0);
			}
			String where = number + "=" + number_update;
			String whereArgs[] = null;
			db.update(TongueTwisterDetails_TABLE, values, where, whereArgs);
		}
		c.close();
		return raw;

	}

	/**
	 * 调取收藏列表
	 */
	public List<TongueTwisterDetails> Db_getAllCollect() {
		List<TongueTwisterDetails> pList = new ArrayList<TongueTwisterDetails>();

		String sql = null;
		sql = "select * from " + TongueTwisterDetails_TABLE
				+ " where isCollect=1";

		Cursor c = db.rawQuery(sql, null);
		try {
			while (c.moveToNext()) {
				TongueTwisterDetails tongueTwisterDetails = new TongueTwisterDetails();
				tongueTwisterDetails.setNumber(c.getInt(c
						.getColumnIndex(number)));
				tongueTwisterDetails.setIsCollect(c.getInt(c
						.getColumnIndex(isCollect)));
				pList.add(tongueTwisterDetails);
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return pList;
	}

	/**
	 * 判断单条绕口令是否收藏
	 */
	public boolean getSingleCollectState(Integer number) {
		int collect_state = 0;
		String sql = null;
		sql = "select * from " + TongueTwisterDetails_TABLE + " where number="
				+ number;

		Cursor c = db.rawQuery(sql, null);
		try {
			while (c.moveToNext()) {
				collect_state = c.getInt(c.getColumnIndex(isCollect));

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}
		if (collect_state == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 刪除指定的行
	 */
	public long Db_loc_delete(Integer number_delete) {
		long raw = 0;
		if (!tableIsExist(TongueTwisterDetails_TABLE)) {
			return raw;
		}
		if (db == null) {
			db = getWritableDatabase();
		}
		Cursor c = db.rawQuery("select * from " + TongueTwisterDetails_TABLE,
				null);
		raw = c.getCount();
		if (raw > 0) {
			String where = number + "=" + number_delete;
			String whereArgs[] = null;
			// 刪除 _ID=1的行
			db.delete(TongueTwisterDetails_TABLE, where, whereArgs);
		}
		c.close();
		return raw;

	}

	/**
	 * 查看所有数据
	 */
	public List<TongueTwisterDetails> Db_getMorePassThrough() {
		List<TongueTwisterDetails> pList = new ArrayList<TongueTwisterDetails>();

		String sql = null;
		sql = "select * from " + TongueTwisterDetails_TABLE;

		Cursor c = db.rawQuery(sql, null);
		try {
			while (c.moveToNext()) {
				TongueTwisterDetails tongueTwisterDetails = new TongueTwisterDetails();
				tongueTwisterDetails.setNumber(c.getInt(c
						.getColumnIndex(number)));
				tongueTwisterDetails.setRatingNum(c.getFloat(c
						.getColumnIndex(ratingNum)));
				tongueTwisterDetails.setIsPassThrough(c.getInt(c
						.getColumnIndex(isPassThrough)));
				tongueTwisterDetails.setIsCollect(c.getInt(c
						.getColumnIndex(isCollect)));
				pList.add(tongueTwisterDetails);
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return pList;
	}

	/**
	 * 查看一条数据
	 */
	public TongueTwisterDetails getOnePassThrough(Integer number_look) {
		TongueTwisterDetails tongueTwisterDetails = new TongueTwisterDetails();
		String sql = null;
		sql = "select * from " + TongueTwisterDetails_TABLE + " where "
				+ number + "=" + number_look;

		Cursor c = db.rawQuery(sql, null);
		try {
			while (c.moveToNext()) {

				tongueTwisterDetails.setNumber(c.getInt(c
						.getColumnIndex(number)));
				tongueTwisterDetails.setRatingNum(c.getFloat(c
						.getColumnIndex(ratingNum)));
				tongueTwisterDetails.setIsPassThrough(c.getInt(c
						.getColumnIndex(isPassThrough)));
				tongueTwisterDetails.setIsCollect(c.getInt(c
						.getColumnIndex(isCollect)));

			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return tongueTwisterDetails;
	}

	/**
	 * @describe table check is exist
	 * @param tabName
	 * @return
	 */
	private boolean tableIsExist(String tabName) {
		boolean result = false;
		if (tabName == null) {
			return result;
		}
		if (db == null) {
			db = this.getWritableDatabase();
		}
		String sql = "select count(*) asc from sqlite_master where type ='table' and name ='"
				+ tabName.trim() + "'";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToNext()) {
			int count = cursor.getInt(0);
			if (count > 0) {
				result = true;
			}
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		return result;
	}

	// 删除这张表
	public void clear() {
		String sql = null;
		sql = "delete from " + TongueTwisterDetails_TABLE;
		db.execSQL(sql);

	}

}
