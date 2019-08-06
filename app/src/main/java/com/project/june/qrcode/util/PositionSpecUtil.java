package com.project.june.qrcode.util;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PositionSpecUtil {

    public static final String SPEC_NONE = "0*0"; //该规格表示没有操作

    public static final String SPEC_POSITION = "7*7"; //定位点

    public static final String SPEC_1_1 = "1*1";
    public static final String SPEC_1_2 = "1*2";
    public static final String SPEC_1_3 = "1*3";
    public static final String SPEC_2_1 = "2*1";
    public static final String SPEC_2_2 = "2*2";
    public static final String SPEC_2_3 = "2*3";
    public static final String SPEC_3_1 = "3*1";

    //判断一个点的规格  并且关联的点没有在remark中（这一步是为了去重，保证一个点只被使用一次）
    //并不是所有规则都要检查   而是需要根据当前素材提供的规格来进行检查
    public static String getPositionSpec(int x, int y, ByteMatrix matrix, HashMap<String, List<Bitmap>> materialMap, HashMap<Point, Boolean> remarkMap) {
        if (checkPosition(x, y, matrix)) {
            return SPEC_POSITION;
        }

        if (materialMap.containsKey(SPEC_3_1) && checkSpec3_1(x, y, matrix, remarkMap)) {
            return SPEC_3_1;
        }

        if (materialMap.containsKey(SPEC_2_2) && checkSpec2_2(x, y, matrix, remarkMap)) {
            return SPEC_2_2;
        }

        if (materialMap.containsKey(SPEC_2_3) && checkSpec2_3(x, y, matrix, remarkMap)) {
            return SPEC_2_3;
        }

        if (materialMap.containsKey(SPEC_1_3) && checkSpec1_3(x, y, matrix, remarkMap)) {
            return SPEC_1_3;
        }

        if (materialMap.containsKey(SPEC_1_2) && checkSpec1_2(x, y, matrix, remarkMap)) {
            return SPEC_1_2;
        }

        if (materialMap.containsKey(SPEC_1_1) && checkSpec1_1(x, y, matrix, remarkMap)) {
            return SPEC_1_1;
        }

        return SPEC_NONE;
    }

    //定位点检查
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

    //1*1不用检查
    public static boolean checkSpec1_1(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth && y < matrixHeight) {
            if (remarkMap.containsKey(new Point(x, y))) {
                return false;
            }

            // 1(x,y)
            return matrix.get(x, y) == 1;
        }
        return false;
    }

    //1*2
    public static boolean checkSpec1_2(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth && y < matrixHeight - 1) {
            if (remarkMap.containsKey(new Point(x, y + 1))) {
                return false;
            }

            // 1(x,y)
            // 1(x,y+1)
            return matrix.get(x, y + 1) == 1;
        }
        return false;
    }

    public static boolean checkSpec1_3(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
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
    public static boolean checkSpec2_1(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
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

    //2*2
    public static boolean checkSpec2_2(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
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

    //2*3
    public static boolean checkSpec2_3(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth - 1 && y < matrixHeight - 2) {
            if (remarkMap.containsKey(new Point(x + 1, y)) ||
                    remarkMap.containsKey(new Point(x, y + 1)) ||
                    remarkMap.containsKey(new Point(x + 1, y + 1)) ||
                    remarkMap.containsKey(new Point(x, y + 2)) ||
                    remarkMap.containsKey(new Point(x + 1, y + 2))) {
                return false;
            }


            // 1(x,y)     1(x+1,y)
            // 1(x,y+1)   1(x+1,y+1)
            // 1(x,y+2)   1(x+1,y+2)
            return matrix.get(x + 1, y) == 1 &&
                    matrix.get(x, y + 1) == 1 && matrix.get(x + 1, y + 1) == 1 &&
                    matrix.get(x, y + 2) == 1 && matrix.get(x + 1, y + 2) == 1;
        }
        return false;
    }

    //3*1
    public static boolean checkSpec3_1(int x, int y, ByteMatrix matrix, HashMap<Point, Boolean> remarkMap) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();
        if (x < matrixWidth - 2 && y < matrixHeight - 1) {
            if (remarkMap.containsKey(new Point(x + 1, y)) ||
                    remarkMap.containsKey(new Point(x + 2, y))) {
                return false;
            }


            // 1(x,y)     1(x+1,y)    1(x+2,y)
            return matrix.get(x + 1, y) == 1 && matrix.get(x + 2, y) == 1;
        }
        return false;
    }

    //根据规格获取某位置的临近点
    public static List<Point> getNearPosition(int x, int y, String spec) {
        List<Point> pointList = new ArrayList();
        switch (spec) {
            case SPEC_POSITION:
                break;
            case SPEC_1_1:
                pointList.add(new Point(x, y));
                break;
            case SPEC_1_2:
                //查到规格为1*2的像素点
                pointList.add(new Point(x, y));
                pointList.add(new Point(x, y + 1));
                break;
            case SPEC_1_3:
                //查到规格为1*3的像素点
                pointList.add(new Point(x, y));
                pointList.add(new Point(x, y + 1));
                pointList.add(new Point(x, y + 2));
                break;
            case SPEC_2_1:
                //查到规格为2*1的像素点
                pointList.add(new Point(x, y));
                pointList.add(new Point(x + 1, y));
                break;
            case SPEC_2_2:
                //查到规格为three的像素点 2*2
                pointList.add(new Point(x, y));
                pointList.add(new Point(x + 1, y));
                pointList.add(new Point(x, y + 1));
                pointList.add(new Point(x + 1, y + 1));
                break;
            case SPEC_3_1:
                //查到规格为3*1的像素点
                pointList.add(new Point(x, y));
                pointList.add(new Point(x + 1, y));
                pointList.add(new Point(x + 2, y));
                break;
            default:
                break;
        }
        return pointList;
    }
}
