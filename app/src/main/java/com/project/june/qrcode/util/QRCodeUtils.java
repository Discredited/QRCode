package com.project.june.qrcode.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
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
import com.project.june.qrcode.special.bean.MaterialBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //创建颜色自定义二维码
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

    //创建DIY自定义二维码
    public static Bitmap createDIYQRCode(String content, int width, int height, List<MaterialBean> materialList) {

        //不允许存在素材为空的情况
        if (null == materialList || materialList.isEmpty()) {
            return null;
        }


        int qrColor = ContextCompat.getColor(Utils.getApp(), R.color.black);
        int bgColor = ContextCompat.getColor(Utils.getApp(), R.color.white);

        Paint paint = new Paint();
        paint.setColor(qrColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

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

            Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            Bitmap bitmap = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(bgColor);
            Canvas canvas = new Canvas(bitmap);

            //保存素材的bitmap用于后面绘制时使用
            //一个spec可能会对应对个素材，所以使用List存起来
            HashMap<String, List<Bitmap>> materialMap = new HashMap<>();
            for (MaterialBean bean : materialList) {
                Bitmap resourceBitmap = BitmapFactory.decodeResource(Utils.getApp().getResources(), bean.materialResourceId);
                if (materialMap.containsKey(bean.spec)) {
                    List<Bitmap> bitmapList = materialMap.get(bean.spec);
                    if (null == bitmapList) {
                        bitmapList = new ArrayList<>();
                    }
                    bitmapList.add(resourceBitmap);
                } else {
                    ArrayList<Bitmap> bitmapList = new ArrayList<>();
                    bitmapList.add(resourceBitmap);
                    materialMap.put(bean.spec, bitmapList);
                }
            }

            //用于记录已使用的像素点
            HashMap<Point, Boolean> remarkMap = new HashMap<>();


            for (int y = 0; y < originalHeight; y++) {
                for (int x = 0; x < originalWidth; x++) {
                    int outputY = outputTop + y * cellWidth;
                    int outputX = outputLeft + x * cellWidth;
                    if (matrix.get(x, y) == 1) {
                        if (remarkMap.containsKey(new Point(x, y))) {
                            continue;
                        }

                        String positionSpec = PositionSpecUtil.getPositionSpec(x, y, matrix, materialMap, remarkMap);

                        List<Bitmap> bitmaps = materialMap.get(positionSpec);
                        if (null == bitmaps || bitmaps.isEmpty()) {
                            continue;
                        }

                        Bitmap bm = bitmaps.get(0);
                        if (bitmaps.size() > 1) {
                            int randomPosition = (int) (Math.random() * 10 % bitmaps.size());
                            bm = bitmaps.get(randomPosition);
                        }

                        Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
                        RectF rectF = new RectF();

                        switch (positionSpec) {
                            case PositionSpecUtil.SPEC_NONE:
                            case PositionSpecUtil.SPEC_POSITION:
                                break;
                            case PositionSpecUtil.SPEC_1_1:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth;
                                rectF.bottom = outputY + cellWidth;
                                break;
                            case PositionSpecUtil.SPEC_1_2:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth;
                                rectF.bottom = outputY + cellWidth * 2;
                                break;
                            case PositionSpecUtil.SPEC_1_3:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth;
                                rectF.bottom = outputY + cellWidth * 3;
                                break;
                            case PositionSpecUtil.SPEC_2_1:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth * 2;
                                rectF.bottom = outputY + cellWidth;
                                break;
                            case PositionSpecUtil.SPEC_2_2:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth * 2;
                                rectF.bottom = outputY + cellWidth * 2;
                                break;
                            case PositionSpecUtil.SPEC_3_1:
                                rectF.left = outputX;
                                rectF.top = outputY;
                                rectF.right = outputX + cellWidth * 3;
                                rectF.bottom = outputY + cellWidth;
                                break;
                        }

                        canvas.drawBitmap(bm, rect, rectF, bitmapPaint);

                        List<Point> pointList = PositionSpecUtil.getNearPosition(x, y, positionSpec);
                        if (null != pointList && pointList.size() > 0) {
                            for (Point point : pointList) {
                                remarkMap.put(point, true);
                            }
                        }
                    }
                }
            }

            //绘制定位点
            List<Bitmap> bitmaps = materialMap.get(PositionSpecUtil.SPEC_POSITION);

            //当定位点图片有三个时，保证每个图片都使用一次，否则随机取图片显示
            if (null != bitmaps && bitmaps.size() >= 3) {
                drawPosition(outputLeft, outputTop, outputWidth, outputHeight, cellWidth, bitmaps, canvas, bitmapPaint, 0, 1, 2);
            } else if (null != bitmaps) {
                int first = (int) (Math.random() * 10 % bitmaps.size());
                int second = (int) (Math.random() * 10 % bitmaps.size());
                int third = (int) (Math.random() * 10 % bitmaps.size());
                drawPosition(outputLeft, outputTop, outputWidth, outputHeight, cellWidth, bitmaps, canvas, bitmapPaint, first, second, third);
            }

            return bitmap;
        } catch (WriterException e) {
            Log.e(TAG, "createQRCodeBitmap: " + e.getMessage());
        }
        return null;
    }

    public static void drawPosition(int outputLeft, int outputTop, int outputWidth, int outputHeight, int cellWidth, List<Bitmap> bitmaps, Canvas canvas, Paint bitmapPaint,
                                    int first, int second, int third) {
        Rect positionRect = new Rect();
        RectF positionRectF = new RectF();
        positionRect.left = 0;
        positionRect.top = 0;
        positionRect.right = bitmaps.get(first).getWidth();
        positionRect.bottom = bitmaps.get(first).getHeight();

        positionRectF.left = outputLeft;
        positionRectF.top = outputTop;
        positionRectF.right = positionRectF.left + cellWidth * 7;
        positionRectF.bottom = positionRectF.top + cellWidth * 7;

        canvas.drawBitmap(bitmaps.get(first), positionRect, positionRectF, bitmapPaint);

        positionRectF.top = outputTop;
        positionRectF.right = outputWidth - outputLeft;
        positionRectF.left = positionRectF.right - cellWidth * 7;
        positionRectF.bottom = positionRectF.top + cellWidth * 7;
        canvas.drawBitmap(bitmaps.get(second), positionRect, positionRectF, bitmapPaint);

        positionRectF.left = outputLeft;
        positionRectF.right = positionRectF.left + cellWidth * 7;
        positionRectF.bottom = outputHeight - outputTop;
        positionRectF.top = positionRectF.bottom - cellWidth * 7;
        canvas.drawBitmap(bitmaps.get(third), positionRect, positionRectF, bitmapPaint);
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
