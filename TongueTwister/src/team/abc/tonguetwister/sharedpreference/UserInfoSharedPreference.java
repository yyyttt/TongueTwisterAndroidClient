package team.abc.tonguetwister.sharedpreference;

import team.abc.bean.UserInfo;
import team.abc.tonguetwister.application.MyApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfoSharedPreference {
	private static final String NAME = "UserInfo";
	private static final SharedPreferences sharedPreferences = MyApplication.getMyAppContext().getSharedPreferences(NAME,Context.MODE_PRIVATE);
	private static final String userId = "userId";
	private static final String userName = "userName";
	private static final String userGender = "userGender";
	private static final String ChallengePassNum = "ChallengePassNum";
	
	public static void storeUserInfo(UserInfo userInfo){
		
		Editor editor = sharedPreferences.edit();
		
		editor.putLong(userId, userInfo.getUserId());
		editor.putString(userName, userInfo.getUserName());
		editor.putInt(userGender, userInfo.getUserGender());
		editor.putInt(ChallengePassNum, userInfo.getChallengePassNum());
		
		editor.commit();
	}
	

	public static UserInfo getUserInfo(){
		UserInfo userInfo = new UserInfo();
		
		userInfo.setUserId(sharedPreferences.getLong(userId, -1));
		userInfo.setUserName(sharedPreferences.getString(userName, ""));
		userInfo.setUserGender(sharedPreferences.getInt(userGender, -1));
		userInfo.setChallengePassNum(sharedPreferences.getInt(ChallengePassNum, 0));
		
		return userInfo;
		
	}
	
	public static void  clearUserInfo(){
		
		Editor editor = sharedPreferences.edit();
		
		editor.clear();
		editor.commit();
		
	}
}
