package team.abc.tonguetwister.javascriptobject;

import team.abc.tonguetwister.fragment.SlidingMenuFragment;
import android.webkit.JavascriptInterface;

public class UserLogoutObject {
	
	private SlidingMenuFragment sMFrg;
	
	public UserLogoutObject(SlidingMenuFragment sMFrg){
		this.sMFrg = sMFrg;
	}
	
	@JavascriptInterface
	public void successLogout(){
		sMFrg.wipeUserInfo();//回调Fragment中方法，清除用户登录信息。
	}
	
}
