package com.example.hu.layaliprokect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.hu.layaliprokect.Entity.QylEntity;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.Fragment.TestFragment;
import com.example.hu.layaliprokect.R;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;


/**
 * Created by yk on 2016/12/26.
 * 牵引力采集结果ListView
 */

public class NvQianYinLiAdapter extends BaseAdapter {
    private Context context;
    private List<QylEntity> list;
    private TestFragment testFragment;
    private DecimalFormat df2 = new DecimalFormat("0.00");

    public NvQianYinLiAdapter(Context context, List<QylEntity> list, TestFragment testFragment) {
        this.context = context;
        this.list = list;
        this.testFragment = testFragment;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<QylEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_qyl1, null);
            viewHolder.textView0 = (TextView) view.findViewById(R.id.item0);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item1);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.item2);
            viewHolder.button = (ImageButton) view.findViewById(R.id.item3);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView0.setText(i + 1 + "");
        viewHolder.textView1.setText(df2.format(list.get(i).getCurrentLi()));
        viewHolder.textView2.setText(list.get(i).getQylCreateTime());
        //删除点击事件
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QylEntity qylEntity = list.get(i);
                list.remove(i);
                updateListView(list);//外面的list也会跟着更新
                testFragment.showInitRes(qylEntity, list);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView textView0;
        TextView textView1;
        TextView textView2;
        ImageButton button;
    }
}
