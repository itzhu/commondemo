package me.itzhu.test.common;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quickly.common.me.appbase.activity.BaseUiActivity;
import quickly.common.me.appbase.util.ToastUtil;
import quickly.common.me.customview.refresh.IRefreshStateChangeListener;
import quickly.common.me.customview.refresh.simple.SimpleChildScrollListener;
import quickly.common.me.customview.refresh.simple.SimpleRefreshLayout;
import quickly.common.me.customview.refresh.simple.SimpleRefreshView;

/**
 * Created by Linhh on 16/4/18.
 */
public class RefreshActivity extends BaseUiActivity {

    private static final String TAG = "RefreshActivity";

    RecyclerView recyclerView;
    SimpleRefreshLayout simpleRefreshLayout;
    SimpleRefreshView refreshView;

    Handler handler;

    int itemCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        handler = new Handler();
        recyclerView = (RecyclerView) findViewById(R.id.refreshRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());

        simpleRefreshLayout = (SimpleRefreshLayout) findViewById(R.id.refreshLayout);
        simpleRefreshLayout.setScrollView(recyclerView);
        simpleRefreshLayout.setChildScrollListener(new SimpleChildScrollListener(recyclerView));

        refreshView = (SimpleRefreshView) findViewById(R.id.refreshView);
        simpleRefreshLayout.setRefreshView(refreshView);

        simpleRefreshLayout.setRStateChangeListener(new IRefreshStateChangeListener() {
            @Override
            public void startRefresh() {
                ToastUtil.showToast("开始刷新");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRefreshLayout.setRefreshCompleted(true);
                    }
                }, 3000);
            }

            @Override
            public void refreshCompleted(boolean success) {
                ToastUtil.showToast("刷新完成");
                if (success) {
                    itemCount += 5;
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void startLoader() {
                ToastUtil.showToast("开始加载");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRefreshLayout.setLoadCompleted(true);
                    }
                }, 3000);
            }

            @Override
            public void loadCompleted(boolean success) {
                ToastUtil.showToast("加载完成");
                if (success) {
                    itemCount += 5;
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_item;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_item = (TextView) itemView.findViewById(R.id.textView);
                tv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        ToastUtil.showToast("item-->" + position);
                        if (position == 1) {
                            simpleRefreshLayout.setRefreshHeaderViewScrollMode(SimpleRefreshLayout.MODE_FIXED);
                            simpleRefreshLayout.setRefreshFooterViewScrollMode(SimpleRefreshLayout.MODE_FIXED);
                        } else if (position == 2) {
                            simpleRefreshLayout.setRefreshHeaderViewScrollMode(SimpleRefreshLayout.MODE_MOVE);
                            simpleRefreshLayout.setRefreshFooterViewScrollMode(SimpleRefreshLayout.MODE_FIXED);
                        } else if (position == 3) {
                            simpleRefreshLayout.setRefreshHeaderViewScrollMode(SimpleRefreshLayout.MODE_MOVE);
                            simpleRefreshLayout.setRefreshFooterViewScrollMode(SimpleRefreshLayout.MODE_MOVE);
                        } else if (position == 4) {
                            simpleRefreshLayout.setRefreshHeaderViewScrollMode(SimpleRefreshLayout.MODE_FIXED);
                            simpleRefreshLayout.setRefreshFooterViewScrollMode(SimpleRefreshLayout.MODE_MOVE);
                        } else if (position == 5) {
                            simpleRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    simpleRefreshLayout.setRefreshStart();
                                }
                            });
                        } else if (position == 6) {
                            simpleRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    simpleRefreshLayout.setLoadStart();
                                }
                            });
                        }
                    }
                });
            }
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, parent, false);
            return new Adapter.ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
            if (position % 5 != 0) {
                holder.tv_item.setTextColor(Color.BLACK);
            } else {
                holder.tv_item.setTextColor(Color.GREEN);
            }

            if (position == 1) {
                holder.tv_item.setText("头部固定 - 底部固定");
            } else if (position == 2) {
                holder.tv_item.setText("头部移动 - 底部固定");
            } else if (position == 3) {
                holder.tv_item.setText("头部移动 - 底部移动");
            } else if (position == 4) {
                holder.tv_item.setText("头部固定 - 底部移动");
            } else if (position == 5) {
                holder.tv_item.setText("开始刷新");
            } else if (position == 6) {
                holder.tv_item.setText("开始加载");
            } else {
                holder.tv_item.setText("item--> " + position);
            }

            holder.tv_item.setTag(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return itemCount;
        }
    }

}
