package com.example.hu.layaliprokect.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hu.layaliprokect.Activity.DataManagerActivity;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.Fragment.DataFragment;
import com.example.hu.layaliprokect.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yk on 2016/12/26.
 * 牵引力采集结果ListView
 */

public class Data2ActivityAdapter extends BaseAdapter {
    private Context context;
    private List<TaskEntity> list;
    private DataManagerActivity dataManagerActivity;
    private DecimalFormat df2 = new DecimalFormat("0.00");
    private List<Boolean> isClick = new ArrayList<>();//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public Data2ActivityAdapter(Context context, List<TaskEntity> list, DataManagerActivity dataManagerActivity) {
        this.context = context;
        this.list = list;
        this.dataManagerActivity = dataManagerActivity;
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                isClick.add(false);
            }
        } else {
            for (int i = 0; i < 6; i++) {
                isClick.add(false);
            }
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<TaskEntity> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_data2, null);
            viewHolder.lltitleName = view.findViewById(R.id.ll_tittle_name);
            viewHolder.textView0 = (TextView) view.findViewById(R.id.item0);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.item1);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.item2);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.item3);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.item4);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView0.setText(i + 1 + "");
        viewHolder.textView1.setText(list.get(i).getUnitName());
        viewHolder.textView2.setText(list.get(i).getPeopleName());
        viewHolder.textView3.setText(list.get(i).getCreateTaskTime());
        viewHolder.textView4.setText(list.get(i).getBeiZhu());

        if (isClick.get(i)) {
            viewHolder.lltitleName.setBackgroundColor(Color.parseColor("#FF03A9F4"));//绿色
        } else {
            viewHolder.lltitleName.setBackgroundColor(Color.parseColor("#B3FFFFFF"));//白色
        }
        //短点击事件
        viewHolder.lltitleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //除了被点击的item置为true外，其他的全部置为false
                for (int i = 0; i < isClick.size(); i++) {
                    isClick.set(i, false);
                }
                isClick.set(i, true);
                notifyDataSetChanged();//刷新界面
                dataManagerActivity.onItemClick(list.get(i));
            }
        });
        //长按点击事件
        viewHolder.lltitleName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TaskEntity taskEntity = list.get(i);
                dataManagerActivity.onItemLongClick(taskEntity);
                //返回true，那么长按监听只执行长按监听中执行的代码，返回false，还会继续响应其他监听中的事件(如短按监听事件)。
                return true;
            }
        });

        return view;
    }

    class ViewHolder {
        LinearLayout lltitleName;
        TextView textView0;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
    }
}
