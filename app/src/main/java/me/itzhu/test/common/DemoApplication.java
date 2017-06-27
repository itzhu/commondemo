package me.itzhu.test.common;

import android.app.Application;

import quickly.common.me.appbase.app.Applib;

/**
 * Created by itzhu on 2017/4/5 0005.
 * desc
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Applib.init(this);
    }
}
