package quickly.common.me.appbase.interfaces;

/**
 * Created by itzhu on 2017/4/8 0008.
 * desc
 */
public interface OnMultiEventlistener<T, D> {
    void onEvent(int eventCode, T data1, D data2);
}