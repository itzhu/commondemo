package quickly.common.me.customview.refresh.simple;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

import quickly.common.me.customview.refresh.IBindChildScrollListener;

/**
 * Created by itzhu on 2017/6/8 0008.
 * desc 检测view的上下拉动
 */
public class SimpleChildScrollListener implements IBindChildScrollListener {
    private View targeView;

    public SimpleChildScrollListener(View targeView) {
        this.targeView = targeView;
    }

    @Override
    public boolean canScrollUp() {
        if (targeView == null) return false;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (targeView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targeView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(targeView, -1) || targeView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targeView, -1);
        }
    }

    @Override
    public boolean canScrollDown() {
        if (targeView == null) return false;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (targeView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) targeView;
                return absListView.getChildCount() > 0 && absListView.getAdapter() != null
                        && (absListView.getLastVisiblePosition() < absListView.getAdapter().getCount() - 1 || absListView.getChildAt(absListView.getChildCount() - 1)
                        .getBottom() < absListView.getPaddingBottom());
            } else {
                return ViewCompat.canScrollVertically(targeView, 1) || targeView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(targeView, 1);
        }
    }
}
