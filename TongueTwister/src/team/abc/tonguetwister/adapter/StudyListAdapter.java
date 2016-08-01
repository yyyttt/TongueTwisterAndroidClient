package team.abc.tonguetwister.adapter;

import java.util.List;

import team.abc.tonguetwister.R;
import team.abc.tonguetwister.activity.StudyActivity;
import team.abc.tonguetwister.activity.StudyTTChooseActivity;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.bean.TongueTwister;
import team.abc.tonguetwister.constant.Level;
import team.abc.tonguetwister.dao.TongueTwisterDetailsDb;
import team.abc.tonguetwister.tools.GroupTitleUtil;
import team.abc.tonguetwister.widget.PinnedSectionListView.PinnedSectionListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StudyListAdapter extends BaseAdapter implements
		PinnedSectionListAdapter {

	private List<TongueTwister> list;
	private Context context;
	private TongueTwister tt;
	private static final String TAG = "StudyListAdapter";

	public StudyListAdapter(List<TongueTwister> list, Context context) {
		this.list = list;
		this.context = context;
		Log.i(TAG, "object create");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder;
		tt = list.get(position);
		
		if(convertView == null){
			holder = new ViewHolder();
			// 说明为标题
			if("".equals(tt.getContent())){
				convertView = LayoutInflater.from(context).inflate(
						R.layout.study_list_title_item, null);
			}else{
				convertView = LayoutInflater.from(context).inflate(
						R.layout.study_list_item, null);
			}
			holder.tv = (TextView) convertView.findViewById(R.id.tv_title);	
			holder.ivCollection = (ImageView) convertView.findViewById(R.id.iv_collection);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(null != holder.ivCollection){
			boolean ttCollectionStatus = TongueTwisterDetailsDb.getDbInstance(
					MyApplication.getMyAppContext()).getSingleCollectState(
							tt.getId());
			if(ttCollectionStatus){
				holder.ivCollection.setVisibility(View.VISIBLE);
			}else{			
				holder.ivCollection.setVisibility(View.INVISIBLE);
			}
		}
		
		holder.tv.setText(tt.getTitle());

		if(! tt.getContent().isEmpty()){
			// 设置 list_item 列表的背景
			holder.tv.setBackgroundResource(R.drawable.selector_list_item);
			holder.tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent(context, StudyActivity.class);
					//intent.putExtra("tonguetwisterIndex", GroupTitleUtil.getTruePosition(position));
					intent.putExtra("tonguetwisterID", list.get(position).getId());
					
					Activity aty = (Activity) context;
					aty.startActivity(intent);
					aty.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					aty.finish();
					
				}
			});			
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView tv;
		ImageView ivCollection;
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub

		if (viewType == 0) {
			return true;
		}

		return false;
	}

	@Override
	public int getViewTypeCount() {
		return 2;// 2种view的类型 baseAdapter中得方法
	}

	@Override
	public int getItemViewType(int position) {

		if (position % (Level.GROUP_TT_NUM + 1) == 0) {
			return 0;
		}

		return 1;
	}

}
