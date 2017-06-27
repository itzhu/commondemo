package quickly.common.me.customview.refresh;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * 只用于滑动状态的监听,不管视图的变化
 * 目的：将手指的滑动状态暴露出去
 */
public class MonitorLayout extends RelativeLayout {

    private static final String TAG = "PullLayout";

    /**
     * 标记的pointY
     * 滑动距离以此为原点计算
     */
    private int markPointY = 0;

    /**
     * 滑动手指ID
     */
    private int mScrollPointerId = -1;

    /**
     * 偏移位置
     */
    private int offsetY = 0;

    /**
     * 初始偏移量
     * 当多个手指按下状态变换时,初始偏移量改变
     */
    private int initOffsetY = 0;

    /**
     * 需要监控View的滑动状态
     */
    private View scrollView;

    private IBindChildScrollListener mChildScrollListener;
    private IScrollChangeListener mScrollChangeListener;

    /**
     * scrollEnable = false时会执行super.dispatchEvent()
     */
    private boolean scrollEnable = true;

    /**
     * 当前状态，0-未偏移状态  1-偏移量大于0  -1-偏移量小于0
     * <p>
     * 因为滑动的offset在快速往返滑动不一定会有为0的状态，故使用此字段标志滑动状态的切换，以此来初始化标记点markPointY{@link #markPointY}
     */
    private static int scrollState = 0;

    public MonitorLayout(@NonNull Context context) {
        super(context);
    }

    public MonitorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MonitorLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollChangeListener(IScrollChangeListener mScrollChangeListener) {
        this.mScrollChangeListener = mScrollChangeListener;
    }

    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }

    public boolean isScrollEnable() {
        return scrollEnable;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mChildScrollListener == null || scrollView == null)
            return super.dispatchTouchEvent(event);

        //是否允许滑动
        if (!isScrollEnable()) return super.dispatchTouchEvent(event);

        final int action = MotionEventCompat.getActionMasked(event);
        final int actionIndex = MotionEventCompat.getActionIndex(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                mScrollPointerId = event.getPointerId(0);// 获取索引为0的手指id
                markPointY = (int) (event.getY() + 0.5f);
                initOffsetY = mScrollChangeListener.currentOffsetY();//当前偏移量
                scrollState = 0;
                Log.e(TAG, "markPointY->" + markPointY);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e(TAG, "ACTION_POINTER_DOWN");
                mScrollPointerId = event.getPointerId(actionIndex);
                markPointY = (int) (event.getY(actionIndex) + 0.5f);
                initOffsetY = mScrollChangeListener.currentOffsetY();
                Log.e(TAG, "markPointY->" + markPointY);
                break;
            case MotionEvent.ACTION_MOVE:
                final int index = event.findPointerIndex(mScrollPointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id " + mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }
                offsetY = (int) (event.getY(index) - markPointY);//偏移距离
                Log.e(TAG, "---A--initOffsetY-->" + initOffsetY);

                /*----- // TODO: 2017/6/9 0009 阻尼效果-简单除2,高级一点的自己去实现-----*/
                offsetY = offsetY / 2 + initOffsetY;
                /*-----------*/
                Log.e(TAG, "---A--offsetY-->" + offsetY + "     ddd" + mChildScrollListener.canScrollDown());
                if (offsetY > 0 && !mChildScrollListener.canScrollUp()) {
                    //Log.e(TAG, "dispatchTouchEvent----A->" + offsetY);
                    if (scrollState <= 0) {
                        markPointY = (int) (event.getY(index) + 0.5f);
                        scrollState = 1;
                        //开始下拉刷新
                        sendCancelEvent(event);
                        return true;
                    }
                    sendScrollOffsetY(offsetY);
                    return true;
                } else if (offsetY < 0 && !mChildScrollListener.canScrollDown()) {
                    Log.d(TAG, "dispatchTouchEvent----B->" + offsetY);
                    if (scrollState >= 0) {
                        markPointY = (int) (event.getY(index) + 0.5f);
                        scrollState = -1;
                        //开始上拉加载
                        sendCancelEvent(event);
                        return true;
                    }
                    Log.e(TAG, "---A--offsetY-->" + offsetY);
                    sendScrollOffsetY(offsetY);
                    return true;
                } else if (scrollState != 0) {
                    Log.d(TAG, "scrollState-->" + scrollState);
                    scrollState = 0;
                    initOffsetY = mScrollChangeListener.currentOffsetY();
                    sendScrollOffsetY(0);
                    sendDownEvent(event);
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(event);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (scrollState == 0) break;

                if (mScrollChangeListener != null) mScrollChangeListener.scrollEnd(offsetY);
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void sendScrollOffsetY(int offsetY) {
        Log.d(TAG, "sendScrollOffsetY-->" + offsetY);
        if (mScrollChangeListener != null) mScrollChangeListener.scrollChanged(offsetY);
    }

    /*-------------重新设置targeView的aCTION_CANCEL和ACTION_DOWN事件- 这段代码copy自PtrFrameLayout-----------*/
    private void sendCancelEvent(MotionEvent event) {
        if (event == null) return;
        MotionEvent last = event;
        MotionEvent e = MotionEvent.obtain(last.getDownTime(), last.getEventTime() + ViewConfiguration.getLongPressTimeout(), MotionEvent.ACTION_CANCEL, last.getX(), last.getY(), last.getMetaState());
        dispatchTouchEventSupper(e);
    }

    private void sendDownEvent(MotionEvent event) {
        if (event == null) return;
        final MotionEvent last = event;
        MotionEvent e = MotionEvent.obtain(last.getDownTime(), last.getEventTime(), MotionEvent.ACTION_DOWN, last.getX(), last.getY(), last.getMetaState());
        dispatchTouchEventSupper(e);
    }

    public boolean dispatchTouchEventSupper(MotionEvent e) {
        return super.dispatchTouchEvent(e);
    }
    /*-----------------------------------*/

    /**
     * 这里不能使用onInterceptTouchEvent
     * onInterceptTouchEvent在一次按下滑动 拦截事件后就不会被执行了。(可以理解为在action_move事件开始的很短时间内会执行，之后就不会执行了。)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 如果onInterceptTouchEvent 没有返回true,onTouchEvent是不会执行的
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }

    /**
     * 手指抬起，参考RecyclerView{@link android.support.v7.widget.RecyclerView#onPointerUp(MotionEvent)}
     *
     * @param e
     */
    private void onPointerUp(MotionEvent e) {
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = e.getPointerId(newIndex);
            markPointY = (int) (e.getY(newIndex) + 0.5f);
            initOffsetY = mScrollChangeListener.currentOffsetY();
        }
    }

    /**
     * 设置targeView
     *
     * @param scrollView
     */
    public void setScrollView(View scrollView) {
        this.scrollView = scrollView;
    }

    /**
     * 设置ChildScrollListener
     *
     * @param mChildScrollListener
     */
    public void setChildScrollListener(IBindChildScrollListener mChildScrollListener) {
        this.mChildScrollListener = mChildScrollListener;
    }

    public View getScrollView() {
        return scrollView;
    }
}
