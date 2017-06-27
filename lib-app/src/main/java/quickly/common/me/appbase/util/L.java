package quickly.common.me.appbase.util;

import android.util.Log;

import quickly.common.me.appbase.BuildConfig;

/**
 * Aauthor- itzhu
 * Date- 2017/3/20 22:12
 * Desc- 日志打印
 */
public class L {

    public static final boolean log_on = true;

    /**
     * @param TAG
     * @param message
     */
    public static void e(String TAG, String message) {
        if (log_on) Log.e(TAG, message);
    }

    /**
     * @param TAG
     * @param message
     * @param tr
     */
    public static void e(String TAG, String message, Throwable tr) {
        if (log_on) Log.e(TAG, message, tr);
    }
}
