package me.itzhu.test.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quickly.common.me.appbase.activity.BaseUiActivity;
import quickly.common.me.appbase.bean.MenuItem;
import quickly.common.me.appbase.interfaces.OnEventlistener;

/**
 * Created by itzhu on 2017/6/27 0027.
 * desc
 */
public class MenuTestActivity extends BaseUiActivity implements OnEventlistener<MenuItem> {

    private TextView tv_menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("menu");
        setContentView(R.layout.activity_menutest);
        tv_menu = $(R.id.tv_menu);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    public void initMenu() {
        super.initMenu();
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(0, 0, "menu1"));
        items.add(new MenuItem(1, R.drawable.aa_icon_back_2, "menu2"));
        items.add(new MenuItem(2, 0, "menu3"));
        items.add(new MenuItem(3, 0, "menu4"));
        createMenus(0, items);
        setOnMenuSelected(this);
    }

    @Override
    public void onEvent(int eventCode, MenuItem data) {
        tv_menu.setText(data.getMenuName());
        closeMenu();
    }
}
