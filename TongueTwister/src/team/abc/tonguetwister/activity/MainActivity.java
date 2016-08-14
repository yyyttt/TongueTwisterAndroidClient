package team.abc.tonguetwister.activity;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.fragment.SlidingContentFragment;
import team.abc.tonguetwister.fragment.SlidingMenuFragment;
import team.abc.tonguetwister.tools.RecordPermissionUtil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

	private SlidingPaneLayout slidingPaneLayout;
	private SlidingMenuFragment menuFragment;
	private SlidingContentFragment contentFragment;
	private DisplayMetrics displayMetrics = new DisplayMetrics();
	private int maxMargin = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		MyApplication.addActivity(this);
		
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.activity_main);
		slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.slidingpanellayout);

		menuFragment = (SlidingMenuFragment) getFragmentManager()
				.findFragmentById(R.id.slidingpane_menu);
		contentFragment = (SlidingContentFragment) getFragmentManager()
				.findFragmentById(R.id.slidingpane_content);

		maxMargin = displayMetrics.heightPixels / 10;
		slidingPaneLayout.setPanelSlideListener(new PanelSlideListener() {

			@Override
			public void onPanelSlide(View panel, float slideOffset) {
				// TODO Auto-generated method stub
				int contentMargin = (int) (slideOffset * maxMargin);
				FrameLayout.LayoutParams contentParams = contentFragment
						.getCurrentViewParams();
				contentParams.setMargins(0, contentMargin, 0, contentMargin);
				contentFragment.setCurrentViewPararms(contentParams);

				float scale = 1 - ((1 - slideOffset) * maxMargin * 2)
						/ (float) displayMetrics.heightPixels;
				menuFragment.getCurrentView().setScaleX(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setScaleY(scale);// 设置缩放的基准点
				menuFragment.getCurrentView().setPivotX(0);// 设置缩放和选择的点
				menuFragment.getCurrentView().setPivotY(
						displayMetrics.heightPixels / 2);
				menuFragment.getCurrentView().setAlpha(slideOffset);
			}

			@Override
			public void onPanelOpened(View panel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPanelClosed(View panel) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	@Override
	public void onBackPressed() {

		final MaterialDialog dialog = new MaterialDialog(this);
		dialog.content("确定要退出吗？").btnText("取消", "确定")
				.showAnim(new BounceTopEnter())
				.dismissAnim(new SlideBottomExit())
				.show();

		dialog.setOnBtnClickL(new OnBtnClickL() {
			@Override
			public void onBtnClick() {
				dialog.dismiss();
			}
		}, new OnBtnClickL() {
			@Override
			public void onBtnClick() {
				MyApplication.exit();
			}
		});

	}

	/*
	 * 打开侧滑栏
	 */
	public void openPanel() {
		// TODO Auto-generated method stub

		slidingPaneLayout.openPane();
	}

}
