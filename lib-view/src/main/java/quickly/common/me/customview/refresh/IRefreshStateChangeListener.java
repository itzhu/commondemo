package quickly.common.me.customview.refresh;

/**
 * Created by itzhu on 2017/6/13 0013.
 * desc
 */
public interface IRefreshStateChangeListener {

    void startRefresh();

    void refreshCompleted(boolean success);

    void startLoader();

    void loadCompleted(boolean success);
}
