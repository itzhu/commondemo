package quickly.common.me.appbase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import quickly.common.me.appbase.manager.AppLifeManager;

/**
 * Author- itzhu
 * Date- 2017/3/7 8:43
 * Desc-
 */
public abstract class ABaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) AppLifeManager.getInstance().addActivity(getActivity());
    }

    /**
     * 简化代码
     * 个人不喜欢使用注解的方式，就用这种方式代替
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T $(@IdRes int id) {
        return (T) getDelegate().findViewById(id);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        if (getActivity() != null) AppLifeManager.getInstance().deleteActivity(getActivity());
        super.onDestroy();
    }

    protected abstract Activity getActivity();

}
