package quickly.common.me.appbase.interfaces;

/**
 * Created by itzhu on 2017/4/8 0008.
 * desc
 */
public interface OnEventlistener<T> {
    void onEvent(int eventCode, T data);
}