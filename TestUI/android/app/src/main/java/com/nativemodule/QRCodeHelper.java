package com.nativemodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.util.FileUtils;
import com.util.ImageUtils;
import com.util.ZXingUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by wyq on 2015/12/22.
 */
public class QRCodeHelper{
    private static final String TAG = "QRFromAlbum";
    public static final int REQ = 10086;
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_CAMERA_ERROR = -1;
    private static final int CODE_IMAGE_ERROR = -2;

    public static final String TYPE_IMAGE = "IMAGE";
    public static final String TYPE_CAMERA = "CAMERA";
    private Activity activity;
    private ScanImageTask mScanImageTask;
    private Callback callback;


    public QRCodeHelper(Activity activity) {
        this.activity = activity;
    }

    public void chooseImage(final Callback callback){
        this.callback = callback;
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT<19){
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }else{
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        this.activity.startActivityForResult(intent, REQ, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult ");
        if(resultCode==Activity.RESULT_OK){
            if (REQ != requestCode) {
                Log.i(TAG, "onActivityResult not Qeq code");
                return;
            }
            File imageFile = FileUtils.getUriFile(this.activity, data.getData());
            if (imageFile != null) {
                mScanImageTask = new ScanImageTask(imageFile);
                mScanImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                sendResult(CODE_IMAGE_ERROR, "图片不存在", null, null, null);
            }
        }else if(resultCode==Activity.RESULT_CANCELED){
            sendResult(CODE_IMAGE_ERROR,"用户取消",null,null,null);
        } else{
            sendResult(CODE_IMAGE_ERROR,"失败",null,null,null);
        }

    }

//    public void startScan() {
//        isScanOpened = true;
//        mQRCodeView.startScan();
//        setResultImage(null);
//    }
//
//    public void stopScan() {
//        if(!isScanOpened) {
//            return;
//        }
//        isScanOpened = false;
//        mQRCodeView.stopScan();
//    }

//
//    private void setResultImage(Bitmap bitmap) {
//        resultImage.setImageBitmap(bitmap);
//        resultImage.setVisibility(bitmap != null ? View.VISIBLE : View.GONE);
//    }

    /**
     * 振动
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) this.activity.getSystemService(activity.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    private void sendResult(int code, String msg, String result, String type,String imagepath) {
        Log.i(TAG, "imagepath:"+imagepath);
        JSONObject json = new JSONObject();
        try {
            json.put("code", code);
            json.put("msg", msg);
            JSONObject data = new JSONObject();
            data.put("result", result);
            data.put("type", type);
            json.put("data", data);
            json.put("imagepath",imagepath);
            json.put("length",4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(this.callback==null){
            Log.i(TAG, "sendResult QRCodeHelper callback");
        }
        this.callback.invoke(json.toString());
    }

    private class ScanImageTask extends AsyncTask<Void, Void, String> {

        private File file;
        private Bitmap bitmap;
        private MultiFormatReader imageScanReader = ZXingUtils.newDefaultReader();
        private String ImagePath = null;
        public ScanImageTask(File file) {
            this.file = file;
        }

        @Override
        public String doInBackground(Void... params) {

            ImagePath = "file://"+file.getAbsolutePath();
            bitmap = ImageUtils.getSmallBitmap(file.getAbsolutePath(), 1000, 1000);
            Result result = ZXingUtils.decode(bitmap, imageScanReader);
            if(result != null) {
                return result.getText();
            }
            return null;
        }


        @Override
        public void onPostExecute(String result) {
            if(mScanImageTask == this) {
                mScanImageTask = null;
            }
            if (result != null) {
                Log.i(TAG, "result:" + result);
                vibrate();
                sendResult(CODE_SUCCESS, "success", result, TYPE_IMAGE,ImagePath);
            } else {
                Log.i(TAG, "result=null");
                sendResult(CODE_IMAGE_ERROR, "解析二维码失败", null, null, ImagePath);
            }
        }

        @Override
        protected void onCancelled() {
            if(mScanImageTask == this) {
                mScanImageTask = null;
            }
        }
    }
}