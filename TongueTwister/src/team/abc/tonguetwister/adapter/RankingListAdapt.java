package team.abc.tonguetwister.adapter;

import java.util.List;

import team.abc.bean.UserInfo;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.Gender;
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
	private List<UserInfo> listItems; // 存放所有用户数据
	private static final String TAG = "RankListAdapter";
	private static final int RANK_NUM = 10; // 最多返回前10名

	public RankingListAdapt(Context context, List<UserInfo> listItems,
			int resource) {
		this.context = context;
		this.listItems = listItems;
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	@Override
	public int getCount() {
		if (listItems.size() > RANK_NUM) {
			return RANK_NUM;
		} else {
			return listItems.size();
		}

	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holderRank;
		if (convertView == null) {
			holderRank = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.ranking_list_item, null);
			// 参数
			holderRank.tvRankNum = (TextView) convertView
					.findViewById(R.id.tv_rank_num);
			holderRank.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			holderRank.tvChallengePassNum = (TextView) convertView
					.findViewById(R.id.tv_challenge_pass_num);
			holderRank.ivUserGender = (ImageView) convertView
					.findViewById(R.id.iv_user_gender);
			convertView.setTag(holderRank);
		} else {
			holderRank = (ViewHolder) convertView.getTag();
		}
		// 对ListItem中每一个用户的信息进行获取
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<" + listItems);
		holderRank.tvRankNum.setText(getItemId(position) + 1 + "");

		holderRank.tvUserName.setText(listItems.get(position).getUserName()
				+ "");
		Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<"
				+ (listItems.get(position).getUserGender() == Gender.FEMALE));
		if (listItems.get(position).getUserGender() == Gender.FEMALE) {
			holderRank.ivUserGender
					.setBackgroundResource(R.drawable.head_portrait_female);
		} else if (listItems.get(position).getUserGender() == Gender.MALE) {
			holderRank.ivUserGender
					.setBackgroundResource(R.drawable.head_portrait_male);
		} else {
			holderRank.ivUserGender
					.setBackgroundResource(R.drawable.head_portrait_secret);
		}
		holderRank.tvChallengePassNum.setText(listItems.get(position)
				.getChallengePassNum() + "");
		return convertView;
	}

	static class ViewHolder {
		ImageView ivUserGender;
		TextView tvRankNum;
		TextView tvUserName;
		TextView tvChallengePassNum;
	}
}
