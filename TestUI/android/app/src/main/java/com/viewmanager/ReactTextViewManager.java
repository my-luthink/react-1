package com.viewmanager;
import android.util.Log;
import android.widget.TextView;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ReactTextViewManager extends SimpleViewManager<TextView> {
    private static final String TAG="MyTextView" ;

    @Override
    public String getName() {
        Log.i(TAG,"getName");
        return "MyTextView";
    }

    @Override
    protected TextView createViewInstance(ThemedReactContext reactContext) {
        Log.i(TAG,"createViewInstance:");
//        TextView textView = new TextView(reactContext);
//        return textView;
        return new TextView(reactContext);
    }

    @ReactProp( name = "text")
    public void setText(final TextView view,String text) {
        Log.i(TAG, "text:"+text);
        view.setText(text);
    }
}