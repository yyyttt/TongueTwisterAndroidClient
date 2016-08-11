package team.abc.tonguetwister.constant;

public class URLConstant {
	
	private static final String BASE = "http://182.61.51.97:8080";
	public static final String LOGIN_URL = BASE + "/NSPClient";
	public static final String LOGOUT_URL = BASE + "/NSPClient/logout.html";
	
	//app分享下载网址（有监控和鉴权）
	public static final String SHARE_URL = BASE+"/TongueTwister/APKDownload";
	//app更新网址（有监控）
	public static final String UPDATE_URL = BASE+"/TongueTwister/APKUpdate";
	
	//用户信息的hessian接口
	public static final String USER_INFO_URL = BASE+"/TongueTwister/UserInfoHessian";
}
