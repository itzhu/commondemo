package quickly.common.me.customview.edittext;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

import quickly.common.me.customview.R;

/**
 * Created by Administrator on 2016/12/13.
 * <p>
 * 清除按钮edittext
 * TODO:2017/1/3 注意不要出现两个drawableRight
 */
public class ClearAutoCompleteText extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    private static final String TAG = "ClearEditText";
    private Drawable clearDrawable;
    private boolean isFocused = true;

    public ClearAutoCompleteText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ClearAutoCompleteText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClearAutoCompleteText(Context context) {
        super(context);
        init(null);
    }

    public void init(AttributeSet attrs) {
        int resId = R.drawable.ic_et_search_clear;
        if (attrs != null) {
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                // L.e(TAG, attrs.getStyleAttribute() + "attrs get No." + i + " attr name: " + attrs.getAttributeName(i) + ", value: " + attrs.getAttributeValue(i));
                if (attrs.getAttributeName(i).equals("drawableRight")) {
                    resId = attrs.getAttributeResourceValue(i, R.drawable.ic_et_search_clear);
                    break;
                }
            }
        }
//         R找不到
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, com.android.internal.R.styleable.TextView);
//        resId =  typedArray.getInt(com.android.internal.R.styleable.TextView_drawableRight,R.drawable.ic_et_search_clear);

        clearDrawable = ContextCompat.getDrawable(getContext(), resId);

        //下面的方法不能获取到自己设置的drawableRight
        //  clearDrawable = getCompoundDrawables()[2];
//        if (clearDrawable == null) {
//            clearDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_et_search_clear);
//        }
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(!TextUtils.isEmpty(text));
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        this.isFocused = focused;
        setClearIconVisible(!TextUtils.isEmpty(getText()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable) setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
            requestLayout();
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = (visible && isFocused && isEnabled()) ? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        requestLayout();
    }

}
