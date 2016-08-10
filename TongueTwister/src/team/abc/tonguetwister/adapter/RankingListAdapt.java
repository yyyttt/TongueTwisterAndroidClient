package team.abc.tonguetwister.adapter;

import java.util.List;
import java.util.Map;

import team.abc.tonguetwister.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankingListAdapt extends BaseAdapter {

	private Context context;
	//private List<Map<String, Object>> listItem;
	private static final String TAG = "RankListAdapter";

	public RankingListAdapt(List<Map<String, Object>> listItem, Context context) {
		this.context = context;
		//this.listItem = listItem;
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return listItem.size();
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holderRank;
		if (convertView == null) {
			holderRank = new ViewHolder();
			// 说明为标题
			convertView = LayoutInflater.from(context).inflate(
					R.layout.ranking_list_item, null);
			// 参数
			holderRank.tvRankNum = (TextView) convertView
					.findViewById(R.id.tv_rank_num);
			holderRank.tvRankName = (TextView) convertView
					.findViewById(R.id.tv_rank_name);
			holderRank.tvRankDone = (TextView) convertView
					.findViewById(R.id.tv_rank_done);
			holderRank.ivRankUser = (ImageView) convertView
					.findViewById(R.id.iv_rank_user);
			convertView.setTag(holderRank);
		} else {
			holderRank = (ViewHolder) convertView.getTag();
		}
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		holderRank.tvRankNum.setText("1");
		holderRank.tvRankName.setText("14578548979");
		holderRank.tvRankDone.setText("12");
		holderRank.ivRankUser
				.setBackgroundResource(R.drawable.head_portrait_secret);
		;
		return convertView;
	}

	static class ViewHolder {
		ImageView ivRankUser;
		TextView tvRankNum;
		TextView tvRankName;
		TextView tvRankDone;
	}
}
