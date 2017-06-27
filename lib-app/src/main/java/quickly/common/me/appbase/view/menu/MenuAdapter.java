package quickly.common.me.appbase.view.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import quickly.common.me.appbase.R;
import quickly.common.me.appbase.bean.MenuItem;
import quickly.common.me.appbase.util.$;

/**
 * Created by itzhu on 2017/6/19 0019.
 * desc
 */
public class MenuAdapter extends BaseAdapter {

    private List<MenuItem> items;
    private LayoutInflater inflater;

    public MenuAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuAdapter.Holder hold;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_menu, parent, false);
            hold = new MenuAdapter.Holder();
            hold.iv_img = $.$(convertView, R.id.iv_img);
            hold.tv_item = $.$(convertView, R.id.tv_item);
            convertView.setTag(hold);
        } else {
            hold = (MenuAdapter.Holder) convertView.getTag();
        }
        hold.iv_img.setImageResource(items.get(position).getImgId());
        hold.tv_item.setText(items.get(position).getMenuName());
        return convertView;
    }

    class Holder {
        ImageView iv_img;
        TextView tv_item;
    }
}
