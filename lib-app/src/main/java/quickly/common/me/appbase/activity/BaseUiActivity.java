package quickly.common.me.appbase.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import quickly.common.me.appbase.bean.MenuItem;
import quickly.common.me.appbase.interfaces.OnEventlistener;
import quickly.common.me.appbase.util.MenuUtil;
import quickly.common.me.appbase.view.BaseUiToolBar;
import quickly.common.me.appbase.R;
import quickly.common.me.appbase.util.$;
import quickly.common.me.appbase.view.menu.MenuPopWindow;

/**
 * Author- itzhu
 * Date- 2017/3/7 8:43
 * Desc-
 * // TODO: 2017/6/27 0027 apptheme请设置为noActionBar
 * ______________________
 * | <      title       |
 * |--------------------|
 * |                    |
 * |                    |
 * |     CONTENT        |
 * |                    |
 * |____________________|
 */

public abstract class BaseUiActivity extends ABaseActivity {

    private BaseUiToolBar toolBar;
    private LinearLayout layout_content;
    private MenuPopWindow menuPopWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initMenu();
    }

    private void initContentView() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.removeAllViews();
        View view = LayoutInflater.from(this).inflate(R.layout.base_activity_baseui, null);
        layout_content = $.$(view, R.id.layout_content);
        toolBar = $.$(view, R.id.baseuitoolbar_actionbar);
        initToolbar();
        content.addView(view);
    }

    /**
     * 设置toolbar
     */
    private void initToolbar() {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setDisplayShowHomeEnabled(false);
            actionbar.setDisplayHomeAsUpEnabled(false);
            //setHomeIcon(R.drawable.aa_icon_back);
            toolBar.setBackImg(R.drawable.sel_bt_back);
            toolBar.setBackClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        toolBar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        toolBar.setTitle(titleId);
    }

    @Override
    public void setContentView(View contentView) {
        layout_content.addView(contentView);
    }

    @Override
    public void setContentView(@IdRes int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, layout_content, true);
    }

    public BaseUiToolBar getToolBar() {
        return toolBar;
    }

    /**
     * 只有一个菜单项
     *
     * @param imgId           0-默认菜单图片
     * @param onClickListener 点击事件
     */
    private void createMenu(int imgId, View.OnClickListener onClickListener) {
        if (imgId != 0) toolBar.setMenuImg(imgId);
        else toolBar.setMenuImg(R.drawable.sel_bg_menu);
        toolBar.setMenuClickListener(onClickListener);
    }

    /**
     * 创建菜单
     *
     * @param resId 右上角隐藏图标
     * @param items 点击图标要显示的菜单
     */
    public void createMenus(int resId, List<MenuItem> items) {
        menuPopWindow = MenuUtil.createMenuPopWindow(this, items);
        createMenu(resId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuPopWindow.isShowing()) showMenu();
                else menuPopWindow.dismiss();
            }
        });
    }

    /**
     * 设置菜单点击事件
     *
     * @param onMenuSelected
     */
    public void setOnMenuSelected(OnEventlistener<MenuItem> onMenuSelected) {
        if (menuPopWindow != null) menuPopWindow.setOnMenuSelectedListener(onMenuSelected);
    }

    /**
     * 如果设置了多个菜单
     * 显示菜单
     */
    public void showMenu() {
        if (menuPopWindow != null && !menuPopWindow.isShowing())
            menuPopWindow.showView(toolBar.getMenuView());
    }

    /**
     * 初始化菜单项显示位置
     */
    public void initMenu() {

    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (menuPopWindow != null && menuPopWindow.isShowing())
            menuPopWindow.dismiss();
    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
