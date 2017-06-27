package quickly.common.me.appbase.util;

import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import quickly.common.me.appbase.R;
import quickly.common.me.appbase.app.Applib;

/**
 * Aauthor- itzhu
 * Date- 2017/3/8 18:30
 * Desc- 弹出框
 */

public class ToastUtil {

    public static void showToast(int resId) {
        TextView textView = (TextView) LayoutInflater.from(Applib.getApplication()).inflate(R.layout.base_toast_view, null, false);
        textView.setText(resId);
        Toast toast = new Toast(Applib.getApplication());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(textView);
        toast.show();
    }

    public static void showToast(String text) {
        TextView textView = (TextView) LayoutInflater.from(Applib.getApplication()).inflate(R.layout.base_toast_view, null, false);
        textView.setText(text);
        Toast toast = new Toast(Applib.getApplication());
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(textView);
        toast.show();
    }

}
