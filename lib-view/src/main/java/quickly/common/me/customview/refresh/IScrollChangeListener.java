package quickly.common.me.customview.refresh;

/**
 * Created by itzhu on 2017/6/9 0009.
 * desc
 */
public interface IScrollChangeListener {

    /**
     * @param offsetY 偏移量
     */
    void scrollChanged(int offsetY);

    void scrollEnd(int offsetY);

    /**
     * 当前偏移量
     */
    int currentOffsetY();
}
