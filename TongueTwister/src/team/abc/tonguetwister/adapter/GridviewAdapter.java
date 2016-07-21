package team.abc.tonguetwister.adapter;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.activity.PassThroughItemActivity;
import team.abc.tonguetwister.bean.TongueTwisterDetails;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;

public class GridviewAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<String> lstDate;
	
	static List<TongueTwisterDetails> list_pass_through = new ArrayList<TongueTwisterDetails>();
	static TongueTwisterDetailsDb tongueTwisterDetailsDb;

	public GridviewAdapter(Context mContext, ArrayList<String> list) {
		this.mContext = mContext;
		lstDate = list;
		tongueTwisterDetailsDb = TongueTwisterDetailsDb.getDbInstance(mContext);

		list_pass_through = tongueTwisterDetailsDb.Db_getMorePassThrough();
//		if (list_pass_through.size() == 0) {
//			list_pass_through = tongueTwisterDetailsDb.passThroughAddMore();
//
//		}

	}

	@Override
	public int getCount() {
		return lstDate.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.iv_lock = (ImageView) convertView.findViewById(R.id.iv_lock);
		viewHolder.tv_num=(TextView)convertView.findViewById(R.id.tv_num);
		viewHolder.lv_gridview_item=(LinearLayout)convertView.findViewById(R.id.lv_gridview_item);
		
		viewHolder.tv_num.setText("第"+(Integer.parseInt(lstDate.get(position)) + 1)+"关");
		if (list_pass_through.get(Integer.parseInt(lstDate.get(position))).getIsPassThrough() == 1
				|| Integer.parseInt(lstDate.get(position)) == 0) {
			final float ratingNum = list_pass_through.get(Integer.parseInt(lstDate.get(position))).getRatingNum();
			viewHolder.iv_lock.setImageResource(R.drawable.icon_lock_un);
			viewHolder.lv_gridview_item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(mContext, PassThroughItemActivity.class);
					intent.putExtra("number", Integer.parseInt(lstDate.get(position)));
					intent.putExtra("ratingNum", ratingNum);

					Activity aty = (Activity) mContext;
					aty.startActivity(intent);
					aty.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					aty.finish();

				}
			});
		} else {
			viewHolder.iv_lock.setImageResource(R.drawable.icon_lock);
			viewHolder.lv_gridview_item.setOnClickListener(null);
		}

		return convertView;
	}
	class ViewHolder {
		public ImageView iv_lock;
		public TextView tv_num;
		public LinearLayout lv_gridview_item;
	}

}
