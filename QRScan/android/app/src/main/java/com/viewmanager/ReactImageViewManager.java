package com.viewmanager;

import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ImageResizeMode;
import com.facebook.react.views.image.ReactImageView;

/**
 * Created by wyq.
 */
public class ReactImageViewManager extends SimpleViewManager<ReactImageView> {
    private static final String TAG="MyImageView" ;
    @Override
    public String getName() {
        return "MyImageView";
    }

    @Override
    protected ReactImageView createViewInstance(ThemedReactContext reactContext) {
        Log.i(TAG, "MyImageView createViewInstance");
        return new ReactImageView(reactContext, Fresco.newDraweeControllerBuilder(), null);
    }

    @ReactProp(name = "src")
    public void setSrc(ReactImageView view, @Nullable String src) {
        Log.i(TAG, "MyImageView setSrc");
        view.setSource(src,null);
    }

    @ReactProp(name = "borderRadius", defaultFloat = 0f)
    public void setBorderRadius(ReactImageView view, float borderRadius) {
        Log.i(TAG, "MyImageView setBorderRadius");
        view.setBorderRadius(borderRadius);
    }

    @ReactProp(name = ViewProps.RESIZE_MODE)
    public void setResizeMode(ReactImageView view, @Nullable String resizeMode) {
        Log.i(TAG, "MyImageView setResizeMode");
        view.setScaleType(ImageResizeMode.toScaleType(resizeMode));
    }
}
