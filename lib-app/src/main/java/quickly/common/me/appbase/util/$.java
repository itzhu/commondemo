package quickly.common.me.appbase.util;

import android.app.Activity;
import android.view.View;

/**
 * @author: itzhu
 * @date: 9:42 2017/2/25
 * @desc: 用于简化代码使用, 用于赋值
 */
public class $ {

    /**
     * 简化代码1  findViewById
     *
     * @param activity
     * @param resId
     * @param <T>
     * @return view.findViewById()
     */
    public static <T extends View> T $(Activity activity, int resId) {
        return (T) activity.findViewById(resId);
    }

    /**
     * 简化代码1  findViewById
     *
     * @param view
     * @param resId
     * @param <T>
     * @return view.findViewById()
     */
    public static <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }


    /*-----------------------onclick事件--------------------------------------*/
    public static void _c(View.OnClickListener onClickListener, View view) {
        view.setOnClickListener(onClickListener);
    }

    public static void _c(View.OnClickListener onClickListener, View... views) {
        for (View view : views) {
            view.setOnClickListener(onClickListener);
        }
    }

    public static void _c(View.OnClickListener onClickListener, View parent, int childId) {
        parent.findViewById(childId).setOnClickListener(onClickListener);
    }

    public static void _c(View.OnClickListener onClickListener, Activity parent, int childId) {
        parent.findViewById(childId).setOnClickListener(onClickListener);
    }

    public static void _c(View.OnClickListener onClickListener, Activity parent, int... childId) {
        for (int id : childId) {
            parent.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    public static void _c(View.OnClickListener onClickListener, View parent, int... childId) {
        for (int id : childId) {
            parent.findViewById(id).setOnClickListener(onClickListener);
        }
    }

    /*---------------------------------onlongclick事件-----------------------------------*/
    public static void _lc(View.OnLongClickListener longClickListener, View view) {
        view.setOnLongClickListener(longClickListener);
    }

    public static void _lc(View.OnLongClickListener longClickListener, View... views) {
        for (View view : views) {
            view.setOnLongClickListener(longClickListener);
        }
    }

    public static void _lc(View.OnLongClickListener longClickListener, View parent, int childId) {
        parent.findViewById(childId).setOnLongClickListener(longClickListener);
    }

    public static void _lc(View.OnLongClickListener longClickListener, Activity parent, int childId) {
        parent.findViewById(childId).setOnLongClickListener(longClickListener);
    }

    public static void _lc(View.OnLongClickListener longClickListener, View parent, int... childId) {
        for (int id : childId) {
            parent.findViewById(id).setOnLongClickListener(longClickListener);
        }
    }

    public static void _lc(View.OnLongClickListener longClickListener, Activity parent, int... childId) {
        for (int id : childId) {
            parent.findViewById(id).setOnLongClickListener(longClickListener);
        }
    }
}
