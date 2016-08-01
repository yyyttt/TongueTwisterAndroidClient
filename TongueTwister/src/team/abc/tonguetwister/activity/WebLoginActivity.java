package team.abc.tonguetwister.activity;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.URLConstant;
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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.os.Build;

public class WebLoginActivity extends Activity {

	
	private WebView wv;
	private ProgressBar proBarLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weblogin_layout);
		
		initView();

	}

	private void initView() {
		wv = (WebView) findViewById(R.id.webView);
		proBarLoading = (ProgressBar) findViewById(R.id.proBar_loading);
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

		wv.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					proBarLoading.setVisibility(View.GONE);
				} else {
					if (proBarLoading.getVisibility() == View.GONE) {
						proBarLoading.setVisibility(View.VISIBLE);
					}
					proBarLoading.setProgress(progress);
				}
			}
		});

		wv.loadUrl(URLConstant.LOGIN_URL);
		wv.addJavascriptInterface(new UserLoginObject(this), "userLoginObject");

	}

}
