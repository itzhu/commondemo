package me.itzhu.test.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import quickly.common.me.appbase.activity.BaseUiActivity;
import quickly.common.me.appbase.util.$;

public class MainActivity extends BaseUiActivity {
    ListView lv_main;

    private String[] items = {"edittext", "refresh", "pulltest", "menuTest"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolBar().setBackImg(-1);
        setTitle("TEST");
        setContentView(R.layout.activity_main);
        lv_main = $.$(this, R.id.lv_mian);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lv_main.setAdapter(adapter);
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(EditTextActivity.class);
                        break;
                    case 1:
                        startActivity(RefreshActivity.class);
                        break;
                    case 2:
                        startActivity(PullTestActivity.class);
                        break;
                    case 3:
                        startActivity(MenuTestActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
