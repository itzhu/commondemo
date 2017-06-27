package quickly.common.me.appbase.util;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import quickly.common.me.appbase.app.Applib;

/**
 * Aauthor- itzhu
 * Date- 2017/3/8 18:42
 * Desc- appinfo信息
 */

public class AppInfoUtil {

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVersionName() {
        try {
            return Applib.getApplication().getPackageManager()
                    .getPackageInfo(Applib.getApplication().getPackageName(),
                            0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
