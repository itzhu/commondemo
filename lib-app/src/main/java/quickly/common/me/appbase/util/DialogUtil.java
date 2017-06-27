package quickly.common.me.appbase.util;

import android.app.Dialog;

import android.graphics.drawable.Drawable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Aauthor- itzhu
 * Date- 2017/3/8 18:30
 * Desc- 对话框util
 */

public class DialogUtil {


    /**
     * @param dialog
     * @param windowAnimation      弹出动画
     * @param backgroundDrawable   背景
     * @param cancelOntouchOutSide 点击区域外部消失
     * @param cancelable           点击返回按钮消失
     */
    public static void initDialog(Dialog dialog, @StyleRes int windowAnimation, Drawable backgroundDrawable,
                                  boolean cancelOntouchOutSide, boolean cancelable) {
        Window window = dialog.getWindow();
        window.setWindowAnimations(windowAnimation);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(backgroundDrawable);//背景透明
        dialog.setCanceledOnTouchOutside(cancelOntouchOutSide);
        dialog.setCancelable(cancelable);
    }

}
