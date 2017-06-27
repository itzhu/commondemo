package quickly.common.me.appbase.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import quickly.common.me.appbase.R;
import quickly.common.me.appbase.util.$;

/**
 * Author- itzhu
 * Date- 2017/3/7 8:43
 * Desc- toolbar
 */
public class BaseUiToolBar extends Toolbar {

    private TextView tv_title;
    private TextView tv_back;
    private TextView tv_menu;

    public BaseUiToolBar(Context context) {
        super(context);
        init();
    }

    public BaseUiToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseUiToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_base_toolbar, this, true);
        tv_title = $.$(this, R.id.baui_title);
        tv_back = $.$(this, R.id.baui_back);
        tv_menu = $.$(this, R.id.baui_menu);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        tv_title.setText(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        tv_title.setText(title);
    }

    public void setBackImg(int resId) {
        if (resId == -1) {
            tv_back.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        tv_back.setCompoundDrawables(drawable, null, null, null);
    }

    public void setBackClickListener(OnClickListener clickListener) {
        $._c(clickListener, tv_back);
    }

    public void setMenuImg(int resId) {
        if (resId == -1) {
            tv_menu.setCompoundDrawables(null, null, null, null);
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        tv_menu.setCompoundDrawables(null, null, drawable, null);
    }

    public void setMenuClickListener(OnClickListener clickListener) {
        $._c(clickListener, tv_menu);
    }

    public TextView getMenuView() {
        return tv_menu;
    }
}
