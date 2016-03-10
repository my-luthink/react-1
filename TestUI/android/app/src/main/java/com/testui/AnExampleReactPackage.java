package com.testui;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.nativemodule.QRFromAlbum;
import com.nativemodule.RecevierModule;
import com.nativemodule.ToastM;
import com.viewmanager.ReactImageViewManager;
import com.viewmanager.ReactTextViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wyq on 2016/1/21.
 */
public class AnExampleReactPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> list = new ArrayList<>();
        list.add(new QRFromAlbum(reactContext));
        list.add(new ToastM(reactContext));
        list.add(new RecevierModule(reactContext));
        return list;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        List<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(new ReactImageViewManager());
        viewManagers.add(new ReactTextViewManager());
        return  viewManagers;
    }
}
