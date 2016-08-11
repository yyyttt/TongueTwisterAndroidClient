package team.abc.tonguetwister.javascriptobject;

import team.abc.bean.UserInfo;
import team.abc.tonguetwister.activity.MainActivity;
import team.abc.tonguetwister.activity.WebLoginActivity;
import team.abc.tonguetwister.sharedpreference.UserInfoSharedPreference;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.baidu.tts.e.l;

public class UserLoginObject {
	
	
	private WebLoginActivity aty;
	
	public UserLoginObject(WebLoginActivity aty){
		this.aty = aty;
	}
	
	@JavascriptInterface
	public void showToast(String name,String id,int gender){
		Toast.makeText(aty, 
				"name="+name+" id="+id +" gender="+gender , Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void nextAty(String name ,String id,int gender){
		
		
		Intent intent = new Intent(aty,MainActivity.class);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(Long.parseLong(id));
		userInfo.setUserName(name);
		userInfo.setUserGender(gender);
		userInfo.setChallengePassNum(0);
		
		//存储用户信息
		UserInfoSharedPreference.storeUserInfo(userInfo);
		
		aty.startActivity(intent);
		aty.finish();
	}
}
