package com.project.june.qrcode.util;

import com.project.june.qrcode.R;
import com.project.june.qrcode.special.bean.MaterialBean;

import java.util.ArrayList;
import java.util.List;

public class MaterialFactory {


    public static final int MATERIAL_COLOR = 0;
    public static final int MATERIAL_COOK = 1;
    public static final int MATERIAL_SCHOOL = 2;

    public static List<MaterialBean> getMaterialList(int materialTheme) {
        List<MaterialBean> list = new ArrayList<>();
        switch (materialTheme) {
            case MATERIAL_COLOR:
                //颜色主题
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_green));
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_orange));
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_red));

                list.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_green_1_1));
                list.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_orange_1_1));
                list.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_green_1_2));
                list.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_orange_1_2));
                list.add(new MaterialBean(2, 2, R.drawable.ic_color_spec_2_2));
                break;
            case MATERIAL_COOK:
                //甜品主题
                list.add(new MaterialBean(7, 7, R.drawable.ic_cook_position));

                list.add(new MaterialBean(1, 1, R.drawable.ic_cook_spec_normal_1_1));
                list.add(new MaterialBean(1, 2, R.drawable.ic_cook_spec_one_1_2));
                list.add(new MaterialBean(2, 2, R.drawable.ic_cook_spec_one_2_2));
                list.add(new MaterialBean(2, 2, R.drawable.ic_cook_spec_two_2_2));
                list.add(new MaterialBean(2, 1, R.drawable.ic_cook_spec_one_2_1));
                break;
            case MATERIAL_SCHOOL:
                //学校主题
                list.add(new MaterialBean(7, 7, R.drawable.ic_city_position));

                list.add(new MaterialBean(1, 1, R.drawable.ic_city_spec_one_1_1));
                list.add(new MaterialBean(1, 1, R.drawable.ic_city_spec_two_1_1));
                list.add(new MaterialBean(1, 1, R.drawable.ic_city_spec_three_1_1));
                list.add(new MaterialBean(1, 3, R.drawable.ic_city_spec_1_3));
                list.add(new MaterialBean(2, 1, R.drawable.ic_city_spec_one_2_1));
                list.add(new MaterialBean(2, 1, R.drawable.ic_city_spec_two_2_1));
                list.add(new MaterialBean(2, 1, R.drawable.ic_city_spec_three_2_1));
                list.add(new MaterialBean(2, 2, R.drawable.ic_city_spec_2_2));
                list.add(new MaterialBean(2, 3, R.drawable.ic_city_spec_2_3));
                list.add(new MaterialBean(3, 1, R.drawable.ic_city_spec_3_1));
                break;
            default:
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_green));
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_orange));
                list.add(new MaterialBean(7, 7, R.drawable.ic_color_position_red));

                list.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_green_1_1));
                list.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_orange_1_1));
                list.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_green_1_2));
                list.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_orange_1_2));
                list.add(new MaterialBean(2, 2, R.drawable.ic_color_spec_2_2));
                break;
        }

        return list;
    }
}
