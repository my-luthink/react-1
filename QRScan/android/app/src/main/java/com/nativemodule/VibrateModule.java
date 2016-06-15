package com.nativemodule;

import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyq.
 */
public class VibrateModule extends ReactContextBaseJavaModule {

    private static final String TAG = "VibrateModule";

    public VibrateModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "VibrateModule";
    }


    @ReactMethod
    private void vibrate() {
        Log.i(TAG, "vibrate");
        Vibrator vibrator = (Vibrator) getCurrentActivity().getSystemService(getCurrentActivity().VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }


    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> Constants = new HashMap<>();
        return Constants;
    }
}
