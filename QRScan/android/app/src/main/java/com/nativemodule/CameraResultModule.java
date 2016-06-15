package com.nativemodule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;
import android.hardware.Camera;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import me.dm7.barcodescanner.core.CameraUtils;

/**
 * Created by wyq.
 */
public class CameraResultModule extends ReactContextBaseJavaModule {
    private static final String TAG = "CameraResultModule";
    public CameraResultModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "CameraResultModule";
    }

    @ReactMethod
    public void showReuslt(String result){
//        final Camera camera = Camera.open();
//        camera.stopPreview();
//        camera.release();
        AlertDialog dialog = new AlertDialog.Builder(getCurrentActivity()).
                setMessage(result).
                setTitle("扫描结果").
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        camera.startPreview();

                    }
                }).create();
        dialog.show();

    }
}
