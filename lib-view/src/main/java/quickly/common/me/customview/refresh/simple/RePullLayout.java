package quickly.common.me.customview.refresh.simple;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import quickly.common.me.customview.refresh.IScrollChangeListener;
import quickly.common.me.customview.refresh.MonitorLayout;
import quickly.common.me.customview.util.AnimationUtil;

/**
 * Created by itzhu on 2017/6/14 0014.
 * desc 重新编写pulllayout
 */
public class RePullLayout extends MonitorLayout implements IScrollChangeListener {
    private IScrollChangeListener mScrollChangeListener;

    public RePullLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RePullLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RePullLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        super.setScrollChangeListener(this);
    }

    @Override
    public void setScrollChangeListener(IScrollChangeListener mStateChangeListener) {
        this.mScrollChangeListener = mStateChangeListener;
    }

    @Override
    public void scrollChanged(int offsetY) {
        getScrollView().setTranslationY(offsetY);
        if (this.mScrollChangeListener != null) this.mScrollChangeListener.scrollChanged(offsetY);
    }

    @Override
    public void scrollEnd(int offsetY) {
        AnimationUtil.smoothTo(getScrollView(), 0, 150);
        if (this.mScrollChangeListener != null) this.mScrollChangeListener.scrollEnd(offsetY);
    }

    @Override
    public int currentOffsetY() {
        return (int) (getScrollView().getTranslationY() + 0.5f);
    }

}
