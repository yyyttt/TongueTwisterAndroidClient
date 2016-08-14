package team.abc.tonguetwister.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.adapter.GridviewAdapter;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.Level;
import team.abc.tonguetwister.widget.ScrollLayout;
import team.abc.tonguetwister.widget.TranslateAnimationWidget;

public class PassThroughActivity extends Activity {

	/** GridView. */
	private LinearLayout linear;
	private GridView gridView;
	private ScrollLayout lst_views;
	private TextView tv_page, tv_title;
	private ImageView runImage;
	LinearLayout.LayoutParams param;
	private ImageButton imgbtn_home;
	public static final int PAGE_SIZE = 16;
	ArrayList<GridView> gridviews = new ArrayList<GridView>();
	ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();// 全部数据的集合集lists.size()==countpage;
	ArrayList<String> lstDate = new ArrayList<String>();// 每一页的数据
	int rockCount = 0;
	GridviewAdapter gridviewAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pass_through);
		for (int i = 0; i < 112; i++) {
			lstDate.add("" + i);
		}

		initView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void initView() {
		imgbtn_home = (ImageButton) findViewById(R.id.imgbtn_home);
		imgbtn_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		lst_views = (ScrollLayout) findViewById(R.id.views);
		tv_page = (TextView) findViewById(R.id.tv_page);
		tv_page.setText("1");
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(Level.ARRAY[0]);
		tv_title.setTypeface(Typeface.DEFAULT_BOLD);
		Constant.init(PassThroughActivity.this);
		param = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.rightMargin = 60;
		param.leftMargin = 60;
		param.topMargin=60;
		param.bottomMargin=60;

		initData();

		initGridview();

		lst_views.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				setCurPage(page);
			}
		});

		runImage = (ImageView) findViewById(R.id.run_image);
		TranslateAnimationWidget.runAnimation(runImage);

	}

	private void initGridview() {

		for (int i = 0; i < Constant.countPages; i++) {

			lst_views.addView(addGridView(i));
		}

	}

	public void initData() {
		Constant.countPages = (int) Math.ceil(lstDate.size() / (float) PAGE_SIZE);

		lists = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < Constant.countPages; i++) {
			lists.add(new ArrayList<String>());
			for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > lstDate.size() ? lstDate.size()
					: PAGE_SIZE * (i + 1)); j++)
				lists.get(i).add(lstDate.get(j));
		}
	}

	public LinearLayout addGridView(int i) {

		linear = new LinearLayout(PassThroughActivity.this);
		gridView = new GridView(getApplicationContext());
		gridviewAdapter = new GridviewAdapter(PassThroughActivity.this, lists.get(i));
		gridView.setAdapter(gridviewAdapter);
		gridView.setNumColumns(4);
		gridView.setColumnWidth(405);
		
		gridView.setHorizontalSpacing(8);
		gridView.setVerticalSpacing(10);

		gridviews.add(gridView);
		linear.addView(gridviews.get(i), param);
		return linear;
		
		
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;
//        int gridviewWidth = (int) (size * (100 + 4) * density);
//        int itemWidth = (int) (100 * density);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
//        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        
	}

	public void setCurPage(final int page) {
		Animation animation = AnimationUtils.loadAnimation(PassThroughActivity.this, R.anim.scale_in);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tv_page.setText((page + 1) + "");
				tv_title.setText(Level.ARRAY[page]);

				tv_page.startAnimation(AnimationUtils.loadAnimation(PassThroughActivity.this, R.anim.scale_out));
				//题目转一圈    20160713 zsc
				//tv_title.startAnimation(AnimationUtils.loadAnimation(PassThroughActivity.this, R.anim.scale_in));
			}
		});
		tv_page.startAnimation(animation);
		tv_title.startAnimation(animation);

	}
	
	/*
	 * 手机键盘的操作
	 */
	/*public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(PassThroughActivity.this,MainActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return false;
	};*/
}
