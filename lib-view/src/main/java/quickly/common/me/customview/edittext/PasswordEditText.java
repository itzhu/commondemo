package quickly.common.me.customview.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import quickly.common.me.customview.R;

/**
 * Created by Administrator on 2016/12/13.
 */
public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {
    private Drawable showDrawable;
    private Drawable hiddenDrawable;

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PasswordEditText(Context context) {
        super(context);
        init(null);
    }

    public void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonEdittext);
        showDrawable = ContextCompat.getDrawable(getContext(), typedArray.getResourceId(R.styleable.CommonEdittext_passwordShowResource, R.drawable.icon_eye_show));
        hiddenDrawable = ContextCompat.getDrawable(getContext(), typedArray.getResourceId(R.styleable.CommonEdittext_passwordHiddenResource, R.drawable.icon_eye_hidden));
        typedArray.recycle();
        showPassword(false);
    }

    /**
     * 如果有serError()
     * // TODO: 2017/1/5  error为显示状态时会出现点击穿透，不过这个可以忽略
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                    && (event.getX() < ((getWidth() - getPaddingRight())));
            if (touchable) {
                if (getInputType() == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    showPassword(false);
                } else {
                    showPassword(true);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param show
     */
    protected void showPassword(boolean show) {
        int selectPosition = getSelectionStart();
        setInputType(show ? EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable eyeDrawable = show ? showDrawable : hiddenDrawable;
        eyeDrawable.setBounds(0, 0, eyeDrawable.getIntrinsicWidth(), eyeDrawable.getIntrinsicHeight());
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], eyeDrawable, getCompoundDrawables()[3]);
        setSelection(selectPosition);//指针移到末尾
    }
}
