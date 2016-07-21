package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.javascriptobject.UserLoginObject;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;

public class WebLoginActivity extends Activity {

	private static final String URL = "http://182.61.51.97:8080/NSPClient";
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weblogin_layout);

		wv = (WebView) findViewById(R.id.webView);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);

		wv.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);

				return true;
			}


		});

		wv.loadUrl(URL);
		wv.addJavascriptInterface(new UserLoginObject(this), "userLoginObject");

	}
	

}
