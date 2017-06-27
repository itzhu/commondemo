package quickly.common.me.appbase.util;

import android.content.Context;

import quickly.common.me.appbase.bean.MenuItem;

import java.util.List;

import quickly.common.me.appbase.view.menu.MenuPopWindow;

/**
 * Created by itzhu on 2017/4/8 0008.
 * desc
 */

public class MenuUtil {

    /**
     * 创建菜单项
     *
     * @param context
     * @param items
     * @return
     */
    public static MenuPopWindow createMenuPopWindow(Context context, List<MenuItem> items) {
        MenuPopWindow popWindow = new MenuPopWindow(context);
        popWindow.setItems(items);
        return popWindow;
    }

}
