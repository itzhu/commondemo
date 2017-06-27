package quickly.common.me.appbase.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import quickly.common.me.appbase.R;
import quickly.common.me.appbase.util.DialogUtil;


/**
 * Created by admin on 2016/5/19.
 */
public class LoadingAtBottomDialog extends Dialog {

    private TextView textView;
    private String tip;

    public LoadingAtBottomDialog(Context context) {
        super(context, R.style.Dim3DialogStyle);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setGravity(Gravity.BOTTOM);
        //getWindow().getDecorView().setPadding(0, 0, 0, 0);
        DialogUtil.initDialog(this, R.style.dialogWindowAnimBottom, new ColorDrawable(Color.TRANSPARENT), false, true);
    }

    public LoadingAtBottomDialog setTip(String tip) {
        this.tip = tip;
        if (textView != null) textView.setText(tip);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_dialog_loading_bottom);
//        textView = (TextView) findViewById(R.id.tv_net_tip);
//        if (tip != null) textView.setText(tip);
    }

}
