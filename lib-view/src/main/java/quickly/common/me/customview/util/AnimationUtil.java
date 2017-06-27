package quickly.common.me.customview.util;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by itzhu on 2017/6/14 0014.
 * desc
 */
public class AnimationUtil {

    /**
     * translationY
     *
     * @param view
     * @param y
     * @param duration
     */
    public static void smoothTo(View view, float y, long duration) {
        if (view == null) return;
        view.clearAnimation();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.animation.ObjectAnimator.ofFloat(view, "translationY", y).setDuration(duration).start();
        }
    }

    public static void rotationTo(ImageView view, int angle, long duration) {
        view.animate().rotation(angle);
    }
}
