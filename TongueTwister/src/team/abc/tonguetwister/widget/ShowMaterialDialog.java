package team.abc.tonguetwister.widget;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.service.DownloadService;

public class ShowMaterialDialog {
	public static void showMaterialDialog(String msg,Context mContext) {
		final MaterialDialog dialog = new MaterialDialog(
				mContext);

		dialog.btnNum(1).content(msg).btnText("确定")
				.showAnim(new BounceTopEnter())
				.dismissAnim(new SlideBottomExit()).show();

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				dialog.cancel();

			}
		});
		 dialog.setCanceledOnTouchOutside(false);
	}
	
	public static void showMaterialDialog2(final Context mContext,String msg,final String downloadUrl) {
		final MaterialDialog dialog = new MaterialDialog(
				mContext);
		
		dialog.setTitle("发现新版本");
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
