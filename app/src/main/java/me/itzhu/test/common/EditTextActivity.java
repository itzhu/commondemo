package me.itzhu.test.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import quickly.common.me.appbase.activity.BaseUiActivity;

/**
 * Created by itzhu on 2017/6/27 0027.
 * desc
 */
public class EditTextActivity extends BaseUiActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }
}
