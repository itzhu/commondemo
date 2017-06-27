package quickly.common.me.appbase.app;

import android.app.Application;

import quickly.common.me.appbase.manager.AppLifeManager;


/**
 * @author itzhu
 * @date 2017/3/7 8:43
 * @desc 主app继承这个
 */

public class Applib {

    public static Application app;

    public static Application getApplication() {
        return app;
    }

    /**
     * 此包的初始化，在application的onCreate里面执行
     *
     * @param app
     */
    public static void init(Application app) {
        Applib.app = app;
        AppLifeManager.getInstance().init();
    }

}
