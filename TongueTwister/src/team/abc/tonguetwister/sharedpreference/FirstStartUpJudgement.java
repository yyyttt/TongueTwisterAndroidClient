package team.abc.tonguetwister.sharedpreference;

import team.abc.tonguetwister.application.MyApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FirstStartUpJudgement {
	public static final String ISFIRSTRECORD = "IsFirstRecord";
	private static final SharedPreferences s = MyApplication.getMyAppContext()
			.getSharedPreferences(ISFIRSTRECORD, Context.MODE_PRIVATE);
	private static final String isFirstNeedRecord = "isFirstNeedRecord";
	private static final String isFirstStartUpForUpdate = "isFirstStartUpForUpdate";

	public static boolean isFirstStartupForUpdate() {
		
		return isFirstNeedSomeThing(isFirstStartUpForUpdate);
	}
	
	public static boolean isFirstNeedRecord() {
		
		return isFirstNeedSomeThing(isFirstNeedRecord);
	}
	
	private static boolean isFirstNeedSomeThing(String something){
		if(s.getBoolean(something,true)){
			
			Editor editor = s.edit();
			editor.putBoolean(something, false);
			editor.commit();
			
			return true;		
		}	
		return false;
	}
	
	

}
