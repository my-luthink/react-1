package com.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyq.
 */
public class ZXingUtils {
    private static final String TAG = "ZXingUtils";

    /**
     * 创建默认的解析二维码的reader
     */
    public static MultiFormatReader newDefaultReader () {
        final List<BarcodeFormat> FORMATS = new ArrayList<>();
        FORMATS.add(BarcodeFormat.QR_CODE);
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, FORMATS);
        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);
        return reader;
    }

    /**
     * 创建解析所有码的reader
     */
    public static MultiFormatReader newAllFormatsReader() {
        final List<BarcodeFormat> FORMATS = new ArrayList<>();
        FORMATS.add(BarcodeFormat.UPC_A);
        FORMATS.add(BarcodeFormat.UPC_E);
        FORMATS.add(BarcodeFormat.EAN_13);
        FORMATS.add(BarcodeFormat.EAN_8);
        FORMATS.add(BarcodeFormat.RSS_14);
        FORMATS.add(BarcodeFormat.CODE_39);
        FORMATS.add(BarcodeFormat.CODE_93);
        FORMATS.add(BarcodeFormat.CODE_128);
        FORMATS.add(BarcodeFormat.ITF);
        FORMATS.add(BarcodeFormat.CODABAR);
        FORMATS.add(BarcodeFormat.QR_CODE);
        FORMATS.add(BarcodeFormat.DATA_MATRIX);
        FORMATS.add(BarcodeFormat.PDF_417);
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, FORMATS);
        MultiFormatReader reader = new MultiFormatReader();
        reader.setHints(hints);
        return reader;
    }

    public static Bitmap createQRCode(String content, int size) throws WriterException {
        return createQRCode(content, size, Color.BLACK);
    }

    public static Bitmap createQRCode(String content, int size, int color) throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
        int[] pixels = new int[size * size];
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * size + x] = color;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, size, 0, 0, size, size);
        return bitmap;
    }

    /**
     * 扫描bitmap中的二维码
     */
    public static Result decode(Bitmap bitmap, MultiFormatReader reader) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            return reader.decodeWithState(binaryBitmap);
        } catch (NotFoundException ignored) {
        } finally {
            reader.reset();
        }
        return null;
    }

    /**
     * 解析图片文件中的二维码
     * @param maxSize 图片最大尺寸 如果图片宽或高超过该值会先缩小再解析 <= 0则不缩小
     */
    public static Result decode(File file, MultiFormatReader reader, int maxSize) {
        String filePath = file.getAbsolutePath();
        BitmapFactory.Options options = null;
        if(maxSize > 0) {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            int wratio = (int) Math.ceil((double) options.outWidth / maxSize);
            int hratio = (int) Math.ceil((double) options.outHeight / maxSize);
            int ratio = wratio;
            if(hratio > ratio) {
                ratio = hratio;
            }
            Log.i(TAG, "decode: ratio=" + ratio);
            if(ratio < 1) {
                ratio = 1;
            }
            options.inSampleSize = ratio;
            options.inJustDecodeBounds = false;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return decode(bitmap, reader);
    }
}
