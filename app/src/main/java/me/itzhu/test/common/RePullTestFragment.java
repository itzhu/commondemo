package me.itzhu.test.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quickly.common.me.appbase.util.ToastUtil;
import quickly.common.me.customview.refresh.simple.RePullLayout;
import quickly.common.me.customview.refresh.simple.SimpleChildScrollListener;

/**
 * Created by itzhu on 2017/6/8 0008.
 * desc
 */
public class RePullTestFragment extends Fragment {

    private static final String DATA_ITEM_COUNT = "itemCount";
    private static final String DATA_TEXT = "text";

    public static RePullTestFragment newInstance(int itemCount, String text) {
        RePullTestFragment fragment = new RePullTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_ITEM_COUNT, itemCount);
        bundle.putString(DATA_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RePullLayout rePullLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pulltest, container, false);
        rePullLayout = (RePullLayout) view.findViewById(R.id.repulllayout);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(getArguments() == null ? "text" : getArguments().getString(DATA_TEXT));
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.recycler_view);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setHasFixedSize(true);
        listView.setAdapter(new Adapter());

        rePullLayout.setScrollView(listView);
        rePullLayout.setChildScrollListener(new SimpleChildScrollListener(listView));

        return view;
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
                    }
                });
            }
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
            if (position % 5 != 0) {
                holder.tv_item.setTextColor(Color.BLACK);
            } else {
                holder.tv_item.setTextColor(Color.GREEN);
            }
            holder.tv_item.setText("item--> " + position);
            holder.tv_item.setTag(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return getArguments() == null || getArguments().getInt(DATA_ITEM_COUNT) <= 0 ? 20 : getArguments().getInt(DATA_ITEM_COUNT);
        }
    }
}

