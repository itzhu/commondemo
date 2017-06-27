package quickly.common.me.appbase.view.menu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import quickly.common.me.appbase.R;
import quickly.common.me.appbase.bean.MenuItem;

import java.util.List;

import quickly.common.me.appbase.interfaces.OnEventlistener;
import quickly.common.me.appbase.view.menu.MenuAdapter;

/**
 * Created by itzhu on 2017/4/8 0008.
 * desc activity菜单弹出框
 */
public class MenuPopWindow extends PopupWindow {

    private View view_bg;

    private ListView lv_items;

    private MenuAdapter adapter;

    private LayoutInflater inflater;

    private OnEventlistener<MenuItem> onMenuSelectedListener;

    private Animation animation;

    public MenuPopWindow(Context context) {
        inflater = LayoutInflater.from(context);
        //窗口布局
        View mainView = inflater.inflate(R.layout.popwindow_menu, null);
        lv_items = (ListView) mainView.findViewById(R.id.lv_items);
        view_bg = mainView.findViewById(R.id.view_bg);
        setContentView(mainView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#40000000"));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        setAnimationStyle(R.style.notAnimation);

        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        adapter = new MenuAdapter(context);
        lv_items.setAdapter(adapter);
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onMenuSelectedListener != null && adapter.getItem(position) != null) {
                    MenuItem item = (MenuItem) adapter.getItem(position);
                    onMenuSelectedListener.onEvent(position, item);
                }
            }
        });

        view_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setItems(List<MenuItem> items) {
        adapter.setItems(items);
    }

    public void setOnMenuSelectedListener(OnEventlistener<MenuItem> onMenuSelectedListener) {
        this.onMenuSelectedListener = onMenuSelectedListener;
    }

    public void showView(View view) {
        lv_items.startAnimation(animation);
        showAsDropDown(view);
    }

}

