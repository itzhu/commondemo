package quickly.common.me.appbase.util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by itzhu on 2017/4/5 0005.
 * desc
 */

public class IntentUtil {

    public static void startActivity(Context context, Class activityClass){
        context.startActivity(new Intent(context,activityClass));
    }
}
