package team.abc.tonguetwister.animation;


import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/** 实现跳跃并落下的动画 */
public class Slider {
	private Animation mAniRight;
	private Animation mAniLeft;
	private SwitchAnimationListener mSwitchListener;

	private View mView;

	/**
	 * 跳跃动画的构造函数
	 * 
	 * @param duration
	 *            每次滑动动画持续时间
	 * @param offset
	 *            动画滑动的高度(像素)
	 */
	public Slider(int duration, int offset) {
		mAniRight = new TranslateAnimation(0,offset,0, 0);
		mAniLeft = new TranslateAnimation(offset, 0, 0, 0);

		mAniRight.setInterpolator(new AccelerateInterpolator());
		mAniLeft.setInterpolator(new DecelerateInterpolator());

		mAniRight.setDuration(duration);
		mAniLeft.setDuration(duration);

		mSwitchListener = new SwitchAnimationListener();

		mAniRight.setAnimationListener(mSwitchListener);
		mAniLeft.setAnimationListener(mSwitchListener);
	}

	/**
	 * 将跳跃动画附到一个View
	 * 
	 * @param view
	 *            准备显示跳跃动画的View(一般是ImageView)
	 */
	public void attachToView(View view) {
		mView = view;

		if (mView != null)
			mView.startAnimation(mAniRight);

	}

	/** 动画切换侦听器 */
	private class SwitchAnimationListener implements
			Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			if (mView == null)
				return;

			if (animation == mAniLeft) {
				if (mView != null)
					mView.startAnimation(mAniRight);
			} else if (animation == mAniRight) {
				if (mView != null)
					mView.startAnimation(mAniLeft);
			}
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
}