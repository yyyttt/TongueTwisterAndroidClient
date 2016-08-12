package team.abc.tonguetwister.tools;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.MaterialDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.provider.Settings;
import android.text.Html;

public class RecordPermissionUtil {
	// 音频获取源
    public static int audioSource = MediaRecorder.AudioSource.MIC;
    // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    public static int sampleRateInHz = 16000;
    // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    public static int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
    public static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区字节大小
    public static int bufferSizeInBytes = 0;
    /**
     * 判断是是否有录音权限
     */
    public static boolean isHasPermission(final Context context){
        bufferSizeInBytes = 0;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord =  new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try{
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        /**
         * 根据开始录音判断是否有录音权限
         */
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
//        	RecordDialog(context);
            return false;
        }
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;

        return true;
    }
    
    public static void RecordDialog(final Context context){
    
            final MaterialDialog dialog = new MaterialDialog(
    				context);

    		dialog.btnNum(1).content("需要开启录音权限").btnText("立即开启")
    				.showAnim(new BounceTopEnter())
    				.dismissAnim(new SlideBottomExit()).show();

    		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

    			@Override
    			public void onDismiss(DialogInterface dialog) {
    				context.startActivity(new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));
    				   
    				dialog.cancel();
    			}
    		});
    		 dialog.setCanceledOnTouchOutside(false);
	
    }
}
