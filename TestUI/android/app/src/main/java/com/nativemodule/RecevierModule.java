package com.nativemodule;

import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyq on 2016/1/26.
 */

public class RecevierModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RecevierModule";

    public RecevierModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RecevierModule";
    }

    @ReactMethod
    public void getResult(String message, final Callback successCallback,final Callback errorCallback) {
        Log.i(TAG, "getResult");
        successCallback.invoke(message +"  success");
    }

//    @Override
//    public boolean canOverrideExistingModule() {
//        return true;
//    }

//    @Override
//    public Map<String, Object> getConstants() {
//        Map<String, Object> Constants = new HashMap<>();
//
//        return Constants;
//    }
}
