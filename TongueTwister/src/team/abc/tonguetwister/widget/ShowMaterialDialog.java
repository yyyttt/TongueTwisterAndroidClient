package team.abc.tonguetwister.widget;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.activity.PassThroughActivity;
import team.abc.tonguetwister.application.MyApplication;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.service.DownloadService;

public class ShowMaterialDialog {
	//一个按钮
	public static void showMaterialDialog(String msg,final Context mContext) {
		final MaterialDialog dialog = new MaterialDialog(
				mContext);

		dialog.btnNum(1).content(msg).btnText("知道了")
				.showAnim(new BounceTopEnter())
				.dismissAnim(new SlideBottomExit()).show();
		if (msg.equals(Constant.RECORD_DENIED)) {
			dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					dialog.cancel();
					Activity mactivity = (Activity) mContext;
					mactivity.startActivity(new Intent(mactivity, PassThroughActivity.class));
					mactivity.overridePendingTransition(R.anim.push_right_in,
							R.anim.push_right_out);
					mactivity.finish();
				}
			});
		}
		else{
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				dialog.cancel();

			}
		});}
		 dialog.setCanceledOnTouchOutside(false);
	}
	//两个按钮
	public static void showMaterialDialog2(final Context mContext,String msg,final String downloadUrl) {
		final MaterialDialog dialog = new MaterialDialog(
				mContext);
		
//		dialog.setTitle("发现新版本");
			dialog.btnNum(2).content(Html.fromHtml(msg).toString()).btnText("以后再说","立即下载")
			.showAnim(new BounceTopEnter())
			.dismissAnim(new SlideBottomExit()).show();
	        dialog.setCanceledOnTouchOutside(false);
            dialog.setOnBtnClickL(new OnBtnClickL() {
	
	        @Override
	        public void onBtnClick() {
		    dialog.dismiss();
	        }
            },new OnBtnClickL() {
	
	       @Override
	       public void onBtnClick() {
		   goToDownload(mContext, downloadUrl);
		   dialog.dismiss();
	       }
           });
	
		 
	}
    private static void goToDownload(Context context, String downloadUrl) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constant.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }
}
