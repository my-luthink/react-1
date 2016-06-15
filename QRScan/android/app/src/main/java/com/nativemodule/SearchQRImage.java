package com.nativemodule;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.qrscan.R;
import com.util.QRCodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wyq.
 */
public class SearchQRImage extends ReactContextBaseJavaModule {
    private static final int BLACK = 0xff000000;
    private static final String TAG = "SearchQRImage";
    public SearchQRImage(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SearchQRImage";
    }

    /*参数不能为null*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void searchImage(final Callback callback){
        String dirpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"QRData"+File.separator+"Image"+File.separator;
        File dir = new File(dirpath);

        if (!dir.getParentFile().exists()){//生成QRData目录
            dir.getParentFile().mkdir();
        }
        if (!dir.exists()){//生成Image目录
            dir.mkdir();
        }
        JSONArray json = new JSONArray();
        callback.invoke(Result(dir,json));

    }

    public String Result(File dir,JSONArray json){
        File[] files = dir.listFiles();
        if (files==null||files.length==0){
            return null;
        }
        for (File f : files) {
            if(f.isDirectory()){
                this.Result(f,json);
            }else if (f.isFile()){
                String path = f.getAbsolutePath();
                String parent = dir.getAbsolutePath();
                String name = path.substring(parent.length()+1,path.length()-5);
                SharedPreferences sharedPreferences = getCurrentActivity().getSharedPreferences("QR", Context.MODE_APPEND);
                String content = sharedPreferences.getString(name,"content");
                JSONObject data = new JSONObject();
                try {
                    data.put("name",name);
                    data.put("content",content);
                    data.put("path",path);
                    Log.i(TAG, "name:"+name);
                    Log.i(TAG, "content:"+content);
                    Log.i(TAG, "path:"+path);
                    json.put(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return json.toString();
    }

}
