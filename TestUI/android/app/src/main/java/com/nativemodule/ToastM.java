package com.nativemodule;

import android.util.Log;
import android.widget.Toast;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by wyq on 2016/1/21.
 */
public class ToastM extends ReactContextBaseJavaModule {

    private static final String TAG = "ToastM";
    private static final String SHORT = "SHORT";
    private static final String LONG = "LONG";

    public ToastM(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ToastM";
    }

    @ReactMethod
    public void show(String message, int duration) {
        Log.i(TAG, "show ToastM");
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }

//    @Override
//    public boolean canOverrideExistingModule() {
//        return true;
//    }

    @Override
    public Map<String, Object> getConstants() {
        Map<String, Object> Constants = new HashMap<>();
        Constants.put(SHORT, Toast.LENGTH_SHORT);
        Constants.put(LONG, Toast.LENGTH_LONG);
        return Constants;
    }
}
