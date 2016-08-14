package team.abc.tonguetwister.fragment;

/**
 *
 * @author zsc
 *  
 */

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.activity.MainActivity;
import team.abc.tonguetwister.activity.PassThroughActivity;
import team.abc.tonguetwister.activity.PkStartActivity;
import team.abc.tonguetwister.activity.StudyModeChooseActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SlidingContentFragment extends Fragment implements OnClickListener {

	private View currentView;
	private RelativeLayout rlStudy, rlPassThrough, rlGroup, rlPk;
	private ImageView ivControl;
	private MainActivity aty;
	private static final String TAG = "SlidingContentFragment";

	public SlidingContentFragment() {

	}

	public void setCurrentViewPararms(FrameLayout.LayoutParams layoutParams) {
		currentView.setLayoutParams(layoutParams);
	}

	public FrameLayout.LayoutParams getCurrentViewParams() {
		return (LayoutParams) currentView.getLayoutParams();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentView = inflater.inflate(R.layout.fragment_content, container,
				false);
		aty = (MainActivity) getActivity();

		rlGroup = (RelativeLayout) currentView.findViewById(R.id.rl_group);
		rlStudy = (RelativeLayout) currentView.findViewById(R.id.rl_study);
		rlPassThrough = (RelativeLayout) currentView
				.findViewById(R.id.rl_pass_through);
		rlPk = (RelativeLayout) currentView.findViewById(R.id.rl_pk);
		ivControl = (ImageView) currentView.findViewById(R.id.iv_control);

		rlStudy.setOnClickListener(this);
		rlPassThrough.setOnClickListener(this);
		rlPk.setOnClickListener(this);
		ivControl.setOnClickListener(this);

		return currentView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onClick(View v) {

		Log.i(TAG, "onClick");

		switch (v.getId()) {
		case R.id.rl_study:

			Intent intentStudy = new Intent();
			intentStudy.setClass(aty, StudyModeChooseActivity.class);
			startActivity(intentStudy);
			//aty.finish();
			break;
		case R.id.rl_pass_through:
			Intent intentPassThrough = new Intent();
			intentPassThrough.setClass(aty, PassThroughActivity.class);
			startActivity(intentPassThrough);
//			aty.finish();
			break;
		case R.id.rl_pk:
			Intent intentPk = new Intent();
			intentPk.setClass(aty, PkStartActivity.class);
			startActivity(intentPk);
//			aty.finish();
			break;

		case R.id.iv_control:

			aty.openPanel();

			break;

		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		rlGroup.startLayoutAnimation();
	}

}
