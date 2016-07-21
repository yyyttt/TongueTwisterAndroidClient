package team.abc.tonguetwister.thread;

import android.content.Context;
import android.util.Log;
import team.abc.tonguetwister.constant.Constant;
import team.abc.tonguetwister.fragment.SlidingMenuFragment;

public class UpdateChecker {


    public static void checkForDialog(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, Constant.TYPE_DIALOG, true).execute();
        } else {
            Log.e(Constant.TAG, "The arg context is null");
        }
    }


}
