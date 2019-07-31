package com.project.june.qrcode;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class QrCodeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }
}
