package quickly.common.me.appbase.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Aauthor- itzhu
 * Date- 2017/3/8 18:37
 * Desc- app生命周期管理,主要对activity的管理
 */

public class AppLifeManager {

    private static AppLifeManager appLifeManager = new AppLifeManager();

    public static AppLifeManager getInstance() {
        return appLifeManager;
    }

    private Stack<Activity> activityStack;

    /**
     * 初始化
     */
    public void init() {
        activityStack = new Stack<>();
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity == null) return;
        this.activityStack.add(activity);
    }

    /**
     * 清除某个activity
     */
    public void deleteActivity(Activity activity) {
        if (activity == null) return;
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 取出当前Activity
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 退出应用
     */
    public void appExit() {
        try {
            for (Activity act : activityStack) {
                if (act != null && !act.isFinishing()) {
                    act.finish();
                }
            }
            activityStack.clear();
        } catch (Exception e) {
        }
    }

    /**
     * 退出应用 结束除activity以外的全部全部activity
     */
    public void appExit(Activity activity) {
        try {
            for (Activity act : activityStack) {
                if (act != null && act != activity) {
                    if (!act.isFinishing()) {
                        act.finish();
                    }
                }
            }
            activityStack.clear();
        } catch (Exception e) {
        }
    }


}
