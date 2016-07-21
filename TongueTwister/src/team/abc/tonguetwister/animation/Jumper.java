package team.abc.tonguetwister.animation;


import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/** 实现跳跃并落下的动画 */
public class Jumper {
	private Animation mAniDown;
	private Animation mAniUp;
	private SwitchAnimationListener mSwitchListener;

	private View mView;

	/**
	 * 跳跃动画的构造函数
	 * 
	 * @param duration
	 *            每次跳跃和落下的动画持续时间
	 * @param offset
	 *            动画跳起的高度(像素)
	 */
	public Jumper(int duration, int offset) {
		mAniDown = new TranslateAnimation(0, 0, -offset, 0);
		mAniUp = new TranslateAnimation(0, 0, 0, -offset);

		mAniDown.setInterpolator(new AccelerateInterpolator());
		mAniUp.setInterpolator(new DecelerateInterpolator());

		mAniDown.setDuration(duration);
		mAniUp.setDuration(duration);

		mSwitchListener = new SwitchAnimationListener();

		mAniDown.setAnimationListener(mSwitchListener);
		mAniUp.setAnimationListener(mSwitchListener);
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
			mView.startAnimation(mAniDown);

	}

	/** 动画切换侦听器 */
	private class SwitchAnimationListener implements
			Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			if (mView == null)
				return;

			if (animation == mAniUp) {
				if (mView != null)
					mView.startAnimation(mAniDown);
			} else if (animation == mAniDown) {
				if (mView != null)
					mView.startAnimation(mAniUp);
			}
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}
}