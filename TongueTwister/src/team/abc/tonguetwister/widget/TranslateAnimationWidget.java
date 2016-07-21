/*
 *imageview 使用translateanimation实现平移动作
 */
package team.abc.tonguetwister.widget;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class TranslateAnimationWidget {

	public  static void runAnimation(final View runImage) {
		final TranslateAnimation left;
		final TranslateAnimation right;
		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f, Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(6000);
		left.setDuration(6000);// 设置位置变化动画的持续时间
		right.setFillAfter(true);// 动画执行完view停留在执行完动画的位置
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);
	}

}
