package com.project.june.qrcode.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import com.project.june.qrcode.R;

import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {

    private static final String TAG = "Sherry";

    public static Bitmap createQRCode(String content) {

        int outputWidth = ConvertUtils.dp2px(300);
        int outputHeight = ConvertUtils.dp2px(300);

        return createQRCode(content, outputWidth, outputHeight);

    }

    //创建普通的二维码
    public static Bitmap createQRCode(String content, int width, int height) {
        int qrColor = ContextCompat.getColor(Utils.getApp(), R.color.black);
        int bgColor = ContextCompat.getColor(Utils.getApp(), R.color.white);

        int padding = ConvertUtils.dp2px(12);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");


        try {
            QRCode qrCode = Encoder.encode(content, ErrorCorrectionLevel.H, hints);
            ByteMatrix matrix = qrCode.getMatrix();
            int originalWidth = matrix.getWidth();
            int originalHeight = matrix.getHeight();

            int outputWidth = Math.max(originalWidth, width);
            int outputHeight = Math.max(originalHeight, height);

            int originalPadding = Math.min(outputWidth / (originalWidth + 2), outputHeight / (originalHeight + 2));
            padding = Math.max(padding, originalPadding);

            int cellWidth = Math.min((outputWidth - padding * 2) / originalWidth, (outputHeight - padding * 2) / originalHeight);

            int outputLeft = (outputWidth - cellWidth * originalWidth) / 2;
            int outputTop = (outputHeight - cellWidth * originalHeight) / 2;

            Paint paint = new Paint();
            paint.setColor(qrColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            Bitmap bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(bgColor);
            Canvas canvas = new Canvas(bitmap);

            for (int y = 0; y < originalHeight; y++) {
                for (int x = 0; x < originalWidth; x++) {
                    int outputY = outputTop + y * cellWidth;
                    int outputX = outputLeft + x * cellWidth;
                    if (matrix.get(x, y) == 1) {
                        canvas.drawRect(outputX, outputY, outputX + cellWidth, outputY + cellWidth, paint);
                    }
                }
            }
            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "createQRCodeBitmap: " + e.getMessage());
        }
        return null;
    }

    //创建自定义二维码
    public static Bitmap createDIYQRCode(String content, int width, int height) {

        int qrColor = ContextCompat.getColor(Utils.getApp(), R.color.black);
        int bgColor = ContextCompat.getColor(Utils.getApp(), R.color.white);

        int specOneColor = ContextCompat.getColor(Utils.getApp(), R.color.blue);
        int specTwoColor = ContextCompat.getColor(Utils.getApp(), R.color.green);
        int specThreeColor = ContextCompat.getColor(Utils.getApp(), R.color.yellow);

        int padding = ConvertUtils.dp2px(12);

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        try {
            QRCode qrCode = Encoder.encode(content, ErrorCorrectionLevel.H, hints);
            ByteMatrix matrix = qrCode.getMatrix();

            int originalWidth = matrix.getWidth();
            int originalHeight = matrix.getHeight();

            int outputWidth = Math.max(originalWidth, width);
            int outputHeight = Math.max(originalHeight, height);

            int originalPadding = Math.min(outputWidth / (originalWidth + 2), outputHeight / (originalHeight + 2));
            padding = Math.max(padding, originalPadding);

            int cellWidth = Math.min((outputWidth - padding * 2) / originalWidth, (outputHeight - padding * 2) / originalHeight);

            int outputLeft = (outputWidth - cellWidth * originalWidth) / 2;
            int outputTop = (outputHeight - cellWidth * originalHeight) / 2;

            Paint paint = new Paint();
            paint.setColor(qrColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            Paint specOnePaint = new Paint();
            specOnePaint.setColor(specOneColor);
            specOnePaint.setStyle(Paint.Style.FILL);
            specOnePaint.setAntiAlias(true);

            Paint specTwoPaint = new Paint();
            specTwoPaint.setColor(specTwoColor);
            specTwoPaint.setStyle(Paint.Style.FILL);
            specTwoPaint.setAntiAlias(true);

            Paint specThreePaint = new Paint();
            specThreePaint.setColor(specThreeColor);
            specThreePaint.setStyle(Paint.Style.FILL);
            specThreePaint.setAntiAlias(true);

            Bitmap bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(bgColor);
            Canvas canvas = new Canvas(bitmap);


            HashMap<Point, Boolean> remarkMap = new HashMap<>();

            for (int y = 0; y < originalHeight; y++) {
                for (int x = 0; x < originalWidth; x++) {
                    int outputY = outputTop + y * cellWidth;
                    int outputX = outputLeft + x * cellWidth;
                    if (matrix.get(x, y) == 1) {
                        if (remarkMap.containsKey(new Point(x, y))) {
                            continue;
                        }

                        boolean isSpecThree = checkSpecThree(x, y, matrix, remarkMap);
                        boolean isSpecTwo = checkSpecTwo(x, y, matrix, remarkMap);
                        boolean isSpecOne = checkSpecOne(x, y, matrix, remarkMap);
                        if (isSpecThree) {
                            //查到规格为three的像素点 2*2
                            remarkMap.put(new Point(x, y), true);
                            remarkMap.put(new Point(x + 1, y), true);
                            remarkMap.put(new Point(x, y + 1), true);
                            remarkMap.put(new Point(x + 1, y + 1), true);

                            canvas.drawRect(outputX, outputY, outputX + cellWidth * 2, outputY + cellWidth * 2, specThreePaint);
                        } else if (isSpecTwo) {
                            //查找规格为two的像素点 1*3
                            remarkMap.put(new Point(x, y), true);
                            remarkMap.put(new Point(x, y + 1), true);
                            remarkMap.put(new Point(x, y + 2), true);

                            canvas.drawRect(outputX, outputY, outputX + cellWidth, outputY + cellWidth * 3, specTwoPaint);
                        } else if (isSpecOne) {
                            //查找规格为one的像素点 1*2
                            remarkMap.put(new Point(x, y), true);
                            remarkMap.put(new Point(x + 1, y), true);

                            canvas.drawRect(outputX, outputY, outputX + cellWidth * 2, outputY + cellWidth, specOnePaint);
                        } else {
                            if (!remarkMap.containsKey(new Point(x, y))) {
                                remarkMap.put(new Point(x, y), true);
                                canvas.drawRect(outputX, outputY, outputX + cellWidth, outputY + cellWidth, paint);
                            }
                        }
                    }
                }
            }
            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "createQRCodeBitmap: " + e.getMessage());
        }
        return null;
    }

    //检查定位点
    public static boolean checkPosition(int x, int y, ByteMatrix matrix) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();

        if (x < 7 && y < 7) {
            return true;
        }

        if (x > matrixWidth - 8 && y < 7) {
            return true;
        }

        if (x < 7 && y > matrixHeight - 8) {
            return true;
        }

        return false;
    }

    //2*2
    public static boolean checkSpecThree(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {

        //定位点检查
        if (checkPosition(x, y, matrix)) {
            return false;
        }

        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth - 1 && y < matrixHeight - 1) {
            if (remarkMap.containsKey(new Point(x + 1, y)) ||
                    remarkMap.containsKey(new Point(x, y + 1)) ||
                    remarkMap.containsKey(new Point(x + 1, y + 1))) {
                return false;
            }


            // 1(x,y)     1(x+1,y)
            // 1(x,y+1)   1(x+1,y+1)
            return matrix.get(x + 1, y) == 1 && matrix.get(x, y + 1) == 1 && matrix.get(x + 1, y + 1) == 1;
        }
        return false;
    }

    //1*3
    public static boolean checkSpecTwo(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        if (checkPosition(x, y, matrix)) {
            return false;
        }

        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth && y < matrixHeight - 2) {
            if (remarkMap.containsKey(new Point(x, y + 1)) ||
                    remarkMap.containsKey(new Point(x, y + 2))) {
                return false;
            }

            // 1(x,y)
            // 1(x,y+1)
            // 1(x,y+2)
            return matrix.get(x, y + 1) == 1 && matrix.get(x, y + 2) == 1;
        }
        return false;
    }

    //2*1
    public static boolean checkSpecOne(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        if (checkPosition(x, y, matrix)) {
            return false;
        }

        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth - 1 && y < matrixHeight) {
            if (remarkMap.containsKey(new Point(x + 1, y))) {
                return false;
            }

            // 1(x,y)  1(x+1,y)
            return matrix.get(x + 1, y) == 1;
        }
        return false;
    }

}
