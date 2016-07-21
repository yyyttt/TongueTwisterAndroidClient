package team.abc.tonguetwister.tools;

import android.app.Activity;
import android.content.Intent;

public class ShareUtil {

	public static void shareTo(Activity activity, String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(Intent.createChooser(intent, activity.getTitle()));
	}

}
