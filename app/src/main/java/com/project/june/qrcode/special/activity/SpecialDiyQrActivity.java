package com.project.june.qrcode.special.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.project.june.qrcode.R;
import com.project.june.qrcode.special.bean.MaterialBean;
import com.project.june.qrcode.util.QRCodeUtils;

import java.util.ArrayList;
import java.util.List;

public class SpecialDiyQrActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText etQrCodeContent;
    private AppCompatImageView ivQrCode;

    private List<MaterialBean> materialList = new ArrayList<>();

    public static void startThis(Context context) {
        Intent starter = new Intent(context, SpecialDiyQrActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_qr);

        ivQrCode = findViewById(R.id.iv_qr_code);
        etQrCodeContent = findViewById(R.id.et_qr_code_content);
        findViewById(R.id.bt_sure).setOnClickListener(this);

        //定位点
        materialList.add(new MaterialBean(7, 7, R.drawable.ic_color_position_green));
        materialList.add(new MaterialBean(7, 7, R.drawable.ic_color_position_orange));
        materialList.add(new MaterialBean(7, 7, R.drawable.ic_color_position_red));

        materialList.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_green_1_1));
        materialList.add(new MaterialBean(1, 1, R.drawable.ic_color_spec_orange_1_1));
        materialList.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_green_1_2));
        materialList.add(new MaterialBean(1, 2, R.drawable.ic_color_spec_orange_1_2));
        materialList.add(new MaterialBean(2, 2, R.drawable.ic_color_spec_2_2));
    }

    private void createBitmap() {
        if (null == etQrCodeContent.getText()) {
            return;
        }
        String content = etQrCodeContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShort(getString(R.string.prompt_input_qr_code_content));
            return;
        }

        int size = ConvertUtils.dp2px(300);
        Bitmap bitmap = QRCodeUtils.createDIYQRCode(content, size, size, materialList);
        if (null == bitmap) {
            ToastUtils.showShort(getString(R.string.prompt_create_qr_code_error));
            return;
        }

        ivQrCode.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                //生成二维码
                createBitmap();
                break;
        }
    }
}
