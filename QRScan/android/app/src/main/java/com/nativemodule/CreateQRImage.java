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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.qrscan.R;
import com.util.QRCodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.Format;
import java.util.Formattable;
import java.util.Hashtable;

import me.dm7.barcodescanner.core.DisplayUtils;

/**
 * Created by wyq.
 */
public class CreateQRImage extends ReactContextBaseJavaModule {
    private static final String TAG = "CreateQRImage";
    public CreateQRImage(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "CreateQRImage";
    }

    /*参数不能为null*/
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @ReactMethod
    public void createImage(String name, String content){
        String dirpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"QRData"+File.separator+"Image"+File.separator;
        File dir = new File(dirpath);

        if (!dir.getParentFile().exists()){//生成QRData目录
            dir.getParentFile().mkdir();
        }
        if (!dir.exists()){//生成Image目录
            dir.mkdir();
        }
        String path = dir.getPath()+File.separator+name+".JPEG";//生成图片路径
        createChineseQRCodeWithLogo(path,content,name);
    }

    //带logo的二维码图片 795dbf
    private void createChineseQRCodeWithLogo(final String path, final String content, final String name) {
        QRCodeEncoder.encodeQRCode(content, 800, Color.parseColor("#dda0dd"), BitmapFactory.decodeResource(getCurrentActivity().getResources(), R.mipmap.heart), new QRCodeEncoder.Delegate() {
            @Override
            public void onEncodeQRCodeSuccess(final Bitmap bitmap) {
                ImageView image = new ImageView(getCurrentActivity());
                image.setImageBitmap(bitmap);
                AlertDialog dialog = new AlertDialog.Builder(getCurrentActivity()).
                        setTitle("图片").setView(image).
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                        camera.startPreview();
                            }
                        }).setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FileOutputStream out = new FileOutputStream(new File(path));
                            bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);//将图片存储在本地
                            out.flush();
                            out.close();
                            SharedPreferences sharedPreferences = getCurrentActivity().getSharedPreferences("QR", Context.MODE_APPEND);
                            SharedPreferences.Editor editor =  sharedPreferences.edit();
                            editor.putString(name,content);
                            editor.commit();
                            Log.i(TAG, "content:"+sharedPreferences.getString(name,"content"));
                        }catch (Exception e){
                            Log.i(TAG, e.getMessage());
                        }
                        Toast.makeText(getCurrentActivity(),"保存成功："+path,Toast.LENGTH_LONG).show();
                    }
                }).create();
                 dialog.show();
            }
            @Override
            public void onEncodeQRCodeFailure() {
                Toast.makeText(getCurrentActivity(), "生成二维码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
