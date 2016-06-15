package com.nativemodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyq.
 */
public class QRFromAlbum extends ReactContextBaseJavaModule implements ActivityEventListener{

    private static final String TAG = "QRFromAlbum";

    private ReactContext reactContext;
    private QRCodeHelper qrCodeHelper;
    private Callback cur_callback;
    public QRFromAlbum(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        qrCodeHelper.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public String getName() {
        return "QRFromAlbum";
    }

    @ReactMethod
    public void chooseImage(final Callback callback) {
        Log.i(TAG, "chooseImage");
        if(getCurrentActivity()==null){
            callback.invoke("activity not exist");
            return;
        }
        if(qrCodeHelper==null){
            qrCodeHelper = new QRCodeHelper(getCurrentActivity());
        }

        this.cur_callback = callback;
        qrCodeHelper.chooseImage(this.cur_callback);
    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> Constants = new HashMap<>();
        return Constants;
    }
}
