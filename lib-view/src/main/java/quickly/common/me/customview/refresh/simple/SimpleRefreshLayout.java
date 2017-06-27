package quickly.common.me.customview.refresh.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import quickly.common.me.customview.refresh.IScrollChangeListener;
import quickly.common.me.customview.refresh.IRefreshStateChangeListener;
import quickly.common.me.customview.refresh.IRefreshView;
import quickly.common.me.customview.refresh.MonitorLayout;
import quickly.common.me.customview.util.AnimationUtil;

/**
 * Created by itzhu on 2017/6/13 0013.
 * desc 下拉刷新1
 * <p>
 * <p 2017-6-14></>
 * 下拉刷新可以改变headerView的高度来实现，但是上拉加载是不可以的。
 * 当上拉时，改变footerView的高度，{@link SimpleChildScrollListener#canScrollDown()}这个函数会交替的true和false,导致界面上下闪动。
 */
public class SimpleRefreshLayout extends MonitorLayout implements IScrollChangeListener {
    private static final String TAG = "RefreshLayout";

    private IRefreshStateChangeListener rstateChangeListener;
    private IScrollChangeListener mScrollChangeListener;

    public static final int MODE_FIXED = 1;
    public static final int MODE_MOVE = 2;

    private int refreshHeaderViewScrollMode = MODE_FIXED;

    private int refreshFooterViewScrollMode = MODE_FIXED;

    public static final int STATE_REFRESH_START = 1;
    public static final int STATE_REFRESH_NORMAL = 2;
    public static final int STATE_REFRESH_ING = 3;

    public static final int STATE_LOAD_START = 4;
    public static final int STATE_LOAD_NORMAL = 5;
    public static final int STATE_LOAD_ING = 6;

    private int currentState = STATE_REFRESH_NORMAL | STATE_LOAD_NORMAL;

    private IRefreshView refreshView;

    public void setRefreshView(SimpleRefreshView refreshView) {
        this.refreshView = refreshView;
    }

    public SimpleRefreshLayout(Context context) {
        super(context);
        init();
    }

    public SimpleRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        super.setScrollChangeListener(this);
    }

    @Override
    public void setScrollChangeListener(IScrollChangeListener mScrollChangeListener) {
        this.mScrollChangeListener = mScrollChangeListener;
    }

    private View getHeaderView() {
        return refreshView.getHeaderView();
    }

    private View getFooterView() {
        return refreshView.getFooterView();
    }

    private int getHeaderHeight() {
        return refreshView.getHeaderHeight();
    }

    private int getFooterHeight() {
        return refreshView.getFooterHeight();
    }

    public int getRefreshHeaderViewScrollMode() {
        return refreshHeaderViewScrollMode;
    }

    public void setRefreshHeaderViewScrollMode(int refreshHeaderViewScrollMode) {
        this.refreshHeaderViewScrollMode = refreshHeaderViewScrollMode;
    }

    public int getRefreshFooterViewScrollMode() {
        return refreshFooterViewScrollMode;
    }

    public void setRefreshFooterViewScrollMode(int refreshFooterViewScrollMode) {
        this.refreshFooterViewScrollMode = refreshFooterViewScrollMode;
    }

    public void setRStateChangeListener(IRefreshStateChangeListener rstateChangeListener) {
        this.rstateChangeListener = rstateChangeListener;
    }

    @Override
    public void scrollChanged(int offsetY) {
        if (this.mScrollChangeListener != null) this.mScrollChangeListener.scrollChanged(offsetY);
        Log.d(TAG, "currentState->" + currentState + "        scrollChanged->" + offsetY);
        if (refreshView == null) return;
        getScrollView().setTranslationY(offsetY);
        /*------------------- 正在刷新或正在加载 ----------------------------*/
        if (currentState == STATE_REFRESH_ING || currentState == STATE_LOAD_ING) {
            return;
        }
        /*---------------------------------------------------------------*/
        if (offsetY > 0) {
            /*-------------设置头部效果---------------*/
            getHeaderView().setVisibility(VISIBLE);
            getFooterView().setVisibility(GONE);

            if (refreshHeaderViewScrollMode == MODE_MOVE) {
                getHeaderView().setTranslationY(offsetY - getHeaderHeight());
            }
            /*-------------------------------------------*/
            if (offsetY > refreshView.getHeaderHeight()) {
                //释放刷新状态
                if (currentState != STATE_REFRESH_START) {
                    currentState = STATE_REFRESH_START;
                    refreshView.changeHeaderView(STATE_REFRESH_START);
                }
            } else {
                //下拉刷新状态
                if (currentState != STATE_REFRESH_NORMAL) {
                    currentState = STATE_REFRESH_NORMAL;
                    refreshView.changeHeaderView(STATE_REFRESH_NORMAL);
                }
            }
        } else if (offsetY < 0) {
          /*-------------设置底部效果---------------*/
            getHeaderView().setVisibility(GONE);
            getFooterView().setVisibility(VISIBLE);

            if (refreshFooterViewScrollMode == MODE_MOVE) {
                getFooterView().setTranslationY(offsetY + getFooterHeight());
            }
            /*-------------------------------------------*/
            if (offsetY < -getFooterHeight()) {
                if (currentState != STATE_LOAD_START) {
                    currentState = STATE_LOAD_START;
                    refreshView.changeFooterView(STATE_LOAD_START);
                }
            } else {
                if (currentState != STATE_LOAD_NORMAL) {
                    currentState = STATE_LOAD_NORMAL;
                    refreshView.changeFooterView(STATE_LOAD_NORMAL);
                }
            }
        }
    }

    @Override
    public void scrollEnd(int offsetY) {
        if (this.mScrollChangeListener != null) this.mScrollChangeListener.scrollEnd(offsetY);
        if (refreshView == null) return;
        //AnimationUtil.smoothTo(getScrollView(), 0, 150);
        getScrollView().setTranslationY(0);
        if (currentState == STATE_REFRESH_ING || currentState == STATE_LOAD_ING) {
            return;
        }
        if (offsetY > 0) {
            getHeaderView().setVisibility(VISIBLE);
            getFooterView().setVisibility(GONE);
            if (offsetY > refreshView.getHeaderHeight()) {
                if (currentState != STATE_REFRESH_ING) {
                    currentState = STATE_REFRESH_ING;
                    refreshView.changeHeaderView(STATE_REFRESH_ING);
                    if (rstateChangeListener != null) rstateChangeListener.startRefresh();
                    setPadding(getPaddingLeft(), getHeaderHeight(), getPaddingRight(), 0);
                    if (refreshHeaderViewScrollMode == MODE_MOVE)
                        getHeaderView().setTranslationY(0);
                    //AnimationUtil.smoothTo(getHeaderView(), 0, 150);
                }
            } else {
                if (currentState != STATE_REFRESH_NORMAL) {
                    setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
                    currentState = STATE_REFRESH_NORMAL;
                    refreshView.changeHeaderView(STATE_REFRESH_NORMAL);
                }
            }
        } else if (offsetY < 0) {
            getHeaderView().setVisibility(GONE);
            getFooterView().setVisibility(VISIBLE);
            if (offsetY < -refreshView.getFooterHeight()) {
                if (currentState != STATE_LOAD_ING) {
                    currentState = STATE_LOAD_ING;
                    refreshView.changeFooterView(STATE_LOAD_ING);
                    if (rstateChangeListener != null) rstateChangeListener.startLoader();
                    setPadding(getPaddingLeft(), 0, getPaddingRight(), getFooterHeight());
                    if (refreshHeaderViewScrollMode == MODE_MOVE)
                        getFooterView().setTranslationY(0);
                    //AnimationUtil.smoothTo(getFooterView(), 0, 150);
                }
            } else {
                if (currentState != STATE_LOAD_NORMAL) {
                    setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
                    currentState = STATE_LOAD_NORMAL;
                    refreshView.changeFooterView(STATE_LOAD_NORMAL);
                }
            }
        }
    }

    @Override
    public int currentOffsetY() {
        return (int) (getScrollView().getTranslationY() + 0.5f);
    }

    /**
     * 开始刷新
     */
    public void setRefreshStart() {
        getHeaderView().setVisibility(VISIBLE);
        getFooterView().setVisibility(GONE);
        if (currentState == STATE_REFRESH_ING) return;

        currentState = STATE_REFRESH_ING;
        refreshView.changeHeaderView(STATE_REFRESH_ING);

        if (rstateChangeListener != null) rstateChangeListener.startRefresh();
        setPadding(getPaddingLeft(), getHeaderHeight(), getPaddingRight(), 0);
        if (refreshHeaderViewScrollMode == MODE_MOVE) getHeaderView().setTranslationY(0);
    }

//    private void setState(int state) {
//        if (currentState == state) return;
//        currentState = state;
//        if (state == (STATE_REFRESH_ING | STATE_REFRESH_NORMAL)) {
//            refreshView.changeHeaderView(state);
//        } else {
//            refreshView.changeFooterView(state);
//        }
//    }

    /**
     * 刷新完成
     * @param success
     */
    public void setRefreshCompleted(boolean success) {
        getHeaderView().setVisibility(VISIBLE);
        getFooterView().setVisibility(GONE);
        if (currentState != STATE_REFRESH_ING) return;

        setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        currentState = STATE_REFRESH_NORMAL;
        refreshView.changeHeaderView(STATE_REFRESH_NORMAL);
        if (rstateChangeListener != null) rstateChangeListener.refreshCompleted(success);
    }

    /**
     * 开始加载
     */
    public void setLoadStart() {
        getHeaderView().setVisibility(GONE);
        getFooterView().setVisibility(VISIBLE);
        if (currentState == STATE_LOAD_ING) return;

        currentState = STATE_LOAD_ING;
        refreshView.changeFooterView(STATE_LOAD_ING);
        if (rstateChangeListener != null) rstateChangeListener.startLoader();
        setPadding(getPaddingLeft(), 0, getPaddingRight(), getFooterHeight());
        if (refreshHeaderViewScrollMode == MODE_MOVE) getFooterView().setTranslationY(0);
    }

    /**
     * 加载完成
     *
     * @param success
     */
    public void setLoadCompleted(boolean success) {
        getHeaderView().setVisibility(GONE);
        getFooterView().setVisibility(VISIBLE);
        if (currentState != STATE_LOAD_ING) return;

        if (rstateChangeListener != null) rstateChangeListener.loadCompleted(success);
        setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        currentState = STATE_LOAD_NORMAL;
        refreshView.changeFooterView(STATE_LOAD_NORMAL);
    }

    /**
     * 滑动结束
     *
     * @param showHeaderView
     * @param showFooterView
     * @param backSmooth
     */
    private void scrollEnd(boolean showHeaderView, boolean showFooterView, boolean backSmooth) {
        getHeaderView().setVisibility(showHeaderView ? VISIBLE : GONE);
        getFooterView().setVisibility(showFooterView ? VISIBLE : GONE);
        if (showHeaderView && backSmooth) AnimationUtil.smoothTo(getHeaderView(), 0, 150);
        else getHeaderView().setTranslationY(0);
        if (showFooterView && backSmooth) AnimationUtil.smoothTo(getFooterView(), 0, 150);
        getFooterView().setTranslationY(0);
    }
}

