package me.itzhu.test.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import quickly.common.me.appbase.activity.BaseUiActivity;

/**
 * Created by itzhu on 2017/6/9 0009.
 * desc
 */
public class PullTestActivity extends BaseUiActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltest);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.view_container, RePullTestFragment.newInstance(35, "testaaaaaaa"), "TAG1")
                .commit();
    }

    @Override
    protected Activity getActivity() {
        return this;
    }
}
