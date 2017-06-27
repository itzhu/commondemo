package quickly.common.me.customview.refresh;

import android.view.View;

/**
 * Created by itzhu on 2017/6/13 0013.
 * desc
 */
public interface IRefreshView {

    void move(int offsetY);

    void end(int offsetY);

    void changeHeaderView(int state);

    void changeFooterView(int state);

    int getHeaderHeight();

    int getFooterHeight();

    View getHeaderView();

    View getFooterView();

    View getView();

}
