package team.abc.tonguetwister.thread;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import team.abc.tonguetwister.R;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.constant.URLConstant;
import team.abc.tonguetwister.tools.AppUtil;
import team.abc.tonguetwister.tools.HttpUtils;
import team.abc.tonguetwister.widget.ShowMaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:21
 */
class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context mContext;
    private int mType;
    private boolean mShowProgressDialog;
   
    CheckUpdateTask(Context context, int type, boolean showProgressDialog) {

        this.mContext = context;
        this.mType = type;
        this.mShowProgressDialog = showProgressDialog;

    }


    protected void onPreExecute() {
        if (mShowProgressDialog) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }


    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }else{        	
        	Toast.makeText(mContext, "连接服务器失败，请检查网络。", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseJson(String result) {
        try {

            JSONObject obj = new JSONObject(result);
            String updateMessage = obj.getString(Constant.APK_UPDATE_CONTENT);
            String apkUrl = obj.getString(Constant.APK_DOWNLOAD_URL);
            int apkCode = obj.getInt(Constant.APK_VERSION_CODE);

            int versionCode = AppUtil.getVersionCode(mContext);

            if (apkCode > versionCode) {
               showDialog(mContext, updateMessage, apkUrl);             
            } else if (mShowProgressDialog) {
                Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.e(Constant.TAG, "parse json error");
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl) {
//        UpdateDialog.show(context, content, apkUrl);
    	ShowMaterialDialog.showMaterialDialog2(context, content, apkUrl);
    }

    @Override
    protected String doInBackground(Void... args) {
        return HttpUtils.get(URLConstant.UPDATE_URL);
    }
}
