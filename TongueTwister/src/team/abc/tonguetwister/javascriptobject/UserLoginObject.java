package team.abc.tonguetwister.javascriptobject;

import team.abc.tonguetwister.activity.MainActivity;
import team.abc.tonguetwister.activity.WebLoginActivity;
import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

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
		intent.putExtra("userName", name);
		intent.putExtra("userID", id);
		intent.putExtra("gender", gender);
		
		aty.startActivity(intent);
		aty.finish();
	}
}
