package com.project.june.qrcode.normal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.project.june.qrcode.R;
import com.project.june.qrcode.util.QRCodeUtils;

public class NormalQrActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText etQrCodeContent;
    private AppCompatImageView ivQrCode;

    public static void startThis(Context context) {
        Intent intent = new Intent(context, NormalQrActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_qr);

        ivQrCode = findViewById(R.id.iv_qr_code);
        etQrCodeContent = findViewById(R.id.et_qr_code_content);
        findViewById(R.id.bt_sure).setOnClickListener(this);
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

        Bitmap bitmap = QRCodeUtils.createQRCode(content);
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
