package quickly.common.me.customview.refresh.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quickly.common.me.customview.R;
import quickly.common.me.customview.refresh.IRefreshView;
import quickly.common.me.customview.util.DensityUtil;

/**
 * Created by itzhu on 2017/6/15 0015.
 * desc
 */
public class SimpleRefreshView extends RelativeLayout implements IRefreshView {
    private View headerView;
    private View footerView;

    private TextView tv_header;
    private ImageView iv_header;
    private ProgressBar pb_header;

    private TextView tv_footer;
    private ImageView iv_footer;
    private ProgressBar pb_footer;

    public SimpleRefreshView(Context context) {
        super(context);
        init();
    }

    public SimpleRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.layout_refresh_header, this, true);
        LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.layout_refresh_footer, this, true);

        setClickable(false);

        headerView = findViewById(R.id.view_header);
        footerView = findViewById(R.id.view_footer);

        tv_header = (TextView) findViewById(R.id.tv_header);
        iv_header = (ImageView) findViewById(R.id.iv_header);
        pb_header = (ProgressBar) findViewById(R.id.pb_header);

        tv_footer = (TextView) findViewById(R.id.tv_footer);
        iv_footer = (ImageView) findViewById(R.id.iv_footer);
        pb_footer = (ProgressBar) findViewById(R.id.pb_footer);
    }

    @Override
    public void move(int offsetY) {
    }

    @Override
    public void end(int offsetY) {

    }

    @Override
    public void changeHeaderView(int state) {
        switch (state) {
            case SimpleRefreshLayout.STATE_REFRESH_NORMAL:
                iv_header.clearAnimation();
                tv_header.setText("下拉刷新");
                iv_header.setVisibility(View.VISIBLE);
                pb_header.setVisibility(View.GONE);
                iv_header.setRotation(0);
                break;
            case SimpleRefreshLayout.STATE_REFRESH_START:
                iv_header.clearAnimation();
                tv_header.setText("释放刷新");
                iv_header.setVisibility(View.VISIBLE);
                pb_header.setVisibility(View.GONE);
                iv_header.animate().rotation(180);
                break;
            case SimpleRefreshLayout.STATE_REFRESH_ING:
                iv_header.clearAnimation();
                tv_header.setText("正在刷新......");
                iv_header.setVisibility(View.GONE);
                pb_header.setVisibility(View.VISIBLE);
                iv_header.setRotation(0);
                break;
        }
    }

    @Override
    public void changeFooterView(int state) {
        switch (state) {
            case SimpleRefreshLayout.STATE_LOAD_NORMAL:
                iv_footer.clearAnimation();
                tv_footer.setText("上拉加载更多");
                iv_footer.setVisibility(View.VISIBLE);
                pb_footer.setVisibility(View.GONE);
                iv_footer.setRotation(0);
                break;
            case SimpleRefreshLayout.STATE_LOAD_START:
                iv_footer.clearAnimation();
                tv_footer.setText("释放开始加载");
                iv_footer.setVisibility(View.VISIBLE);
                pb_footer.setVisibility(View.GONE);
                iv_footer.animate().rotation(180);
                break;
            case SimpleRefreshLayout.STATE_LOAD_ING:
                iv_footer.clearAnimation();
                tv_footer.setText("正在加载......");
                iv_footer.setVisibility(View.GONE);
                pb_footer.setVisibility(View.VISIBLE);
                iv_footer.setRotation(0);
                break;
        }
    }

    @Override
    public int getHeaderHeight() {
        return DensityUtil.dip2px(getContext().getApplicationContext(), 64);
    }

    @Override
    public int getFooterHeight() {
        return DensityUtil.dip2px(getContext().getApplicationContext(), 64);
    }

    @Override
    public View getHeaderView() {
        return headerView;
    }

    @Override
    public View getFooterView() {
        return footerView;
    }

    @Override
    public View getView() {
        return this;
    }

}
