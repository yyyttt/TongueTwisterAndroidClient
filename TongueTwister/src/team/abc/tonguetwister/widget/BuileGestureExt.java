package team.abc.tonguetwister.widget;

import android.app.Activity;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import team.abc.tonguetwister.constant.Constant;

public class BuileGestureExt {

	private OnGestureResult onGestureResult;
	private Context mContext;

	public BuileGestureExt(Context c, OnGestureResult onGestureResult) {
		this.mContext = c;
		this.onGestureResult = onGestureResult;
		Constant.init((Activity)c);
	}

	public GestureDetector Buile() {
		return new GestureDetector(mContext, onGestureListener);
	}

	private GestureDetector.OnGestureListener onGestureListener = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			float x = e2.getX() - e1.getX();
			float y = e2.getY() - e1.getY();
			// 限制必须得划过屏幕的1/4才能算划过
			float x_limit = Constant.screenWidth / 4;
			float y_limit = Constant.screenHeight / 4;
			float x_abs = Math.abs(x);
			float y_abs = Math.abs(y);
			if (x_abs >= y_abs) {
				// gesture left or right
				if (x > x_limit || x < -x_limit) {
					if (x > 0) {
						// right
						doResult(Constant.GESTURE_RIGHT);
					} else if (x <= 0) {
						// left
						doResult(Constant.GESTURE_LEFT);
					}
				}
			} else {
				// gesture down or up
				if (y > y_limit || y < -y_limit) {
					if (y > 0) {
						// down
						doResult(Constant.GESTURE_DOWN);
					} else if (y <= 0) {
						// up
						doResult(Constant.GESTURE_UP);
					}
				}
			}
			return true;
		}

	};

	public void doResult(int result) {
		if (onGestureResult != null) {
			onGestureResult.onGestureResult(result);
		}
	}

	public interface OnGestureResult {
		public void onGestureResult(int direction);
	}

}
