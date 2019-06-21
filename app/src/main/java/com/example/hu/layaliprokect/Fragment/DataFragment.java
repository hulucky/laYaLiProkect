package com.example.hu.layaliprokect.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.manager.QylEntityDao;
import com.db.manager.TaskEntityDao;
import com.example.hu.layaliprokect.Adapter.Data1Adapter;
import com.example.hu.layaliprokect.Adapter.Data2Adapter;
import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Entity.QylEntity;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.R;
import com.mchsdk.paysdk.mylibrary.ListFragment;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DataFragment extends ListFragment {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.item0)
    TextView item0;
    @BindView(R.id.item1)
    TextView item1;
    @BindView(R.id.item2)
    TextView item2;
    @BindView(R.id.item3)
    TextView item3;
    @BindView(R.id.ll_tittle_name)
    LinearLayout llTittleName;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.tv_pingJun)
    TextView tvPingJun;
    @BindView(R.id.tv_pingJunValue)
    TextView tvPingJunValue;
    @BindView(R.id.ll_pingJun)
    LinearLayout llPingJun;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.tv_leiJi)
    TextView tvLeiJi;
    @BindView(R.id.tv_leiJiValue)
    TextView tvLeiJiValue;
    @BindView(R.id.ll_leiJi)
    LinearLayout llLeiJi;
    @BindView(R.id.line5)
    View line5;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.btn_chaXun)
    Button btnChaXun;
    @BindView(R.id.btn_quanBu)
    Button btnQuanBu;
    @BindView(R.id.listView2)
    ListView listView2;

    private List<TaskEntity> taskEntities;
    private List<QylEntity> qylEntities;
    private TaskEntityDao taskEntityDao;
    private QylEntityDao qylEntityDao;
    private Context context;
    private Data1Adapter data1Adapter;
    private Data2Adapter data2Adapter;
    private DecimalFormat df2 = new DecimalFormat("0.00");

    @Override
    protected void lazyLoadData() {
        taskEntities = taskEntityDao.loadAll();
        qylEntities = qylEntityDao.loadAll();
        if (taskEntities.size() != 0) {
            listView2.setVisibility(View.VISIBLE);
            Collections.reverse(taskEntities);//倒序
            data2Adapter = new Data2Adapter(context, taskEntities, DataFragment.this);
            listView2.setAdapter(data2Adapter);
        } else {
            listView2.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
        }
        if (qylEntities.size() != 0) {
            listview.setVisibility(View.VISIBLE);
            Collections.reverse(qylEntities);
            data1Adapter = new Data1Adapter(context, qylEntities, DataFragment.this);
            listview.setAdapter(data1Adapter);
            String pingJun = "";//平均
            float leiJi = 0;//累积
            for (int i = 0; i < qylEntities.size(); i++) {
                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
            }
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
            tvLeiJiValue.setText(df2.format(leiJi));
        } else {
            listview.setVisibility(View.INVISIBLE);
            tvPingJunValue.setText("--");
            tvLeiJiValue.setText("--");
            Toast.makeText(context, "暂无力数据", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        context = getActivity();
        taskEntities = new ArrayList<>();
        qylEntities = new ArrayList<>();
        taskEntityDao = MyApp.getInstance().getmDaoSession().getTaskEntityDao();
        qylEntityDao = MyApp.getInstance().getmDaoSession().getQylEntityDao();
    }

    public static DataFragment newInstance(int position) {
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        dataFragment.setArguments(bundle);
        return dataFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_data;
    }


    @OnClick({R.id.btn_chaXun, R.id.btn_quanBu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chaXun://查询点击事件
                String Et1 = et1.getText().toString().trim();
                String Et2 = this.et2.getText().toString().trim();
                String Et3 = this.et3.getText().toString().trim();
                String Et4 = this.et4.getText().toString().trim();
                if (Et1.equals("") && !Et2.equals("") && !Et4.equals("")) {//et1为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.PeopleName.like(Et2),
                            TaskEntityDao.Properties.BeiZhu.like(Et4)).list();
                } else if (!Et1.equals("") && Et2.equals("") && !Et4.equals("")) {//et2为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like(Et1),
                            TaskEntityDao.Properties.BeiZhu.like(Et4)).list();
                } else if (!Et1.equals("") && !Et2.equals("") && Et4.equals("")) {//et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like(Et1),
                            TaskEntityDao.Properties.PeopleName.like(Et2)).list();
                } else if (Et1.equals("") && Et2.equals("") && !Et4.equals("")) {//et1、et2为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.BeiZhu.like(Et4)).list();
                } else if (Et1.equals("") && !Et2.equals("") && Et4.equals("")) {//et1、et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.PeopleName.like(Et2)).list();
                } else if (!Et1.equals("") && Et2.equals("") && Et4.equals("")) {//et2、et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like(Et1)).list();
                } else if (Et1.equals("") && Et2.equals("") && Et4.equals("")) {//et1、et2、et4全为空
                    taskEntities = taskEntityDao.loadAll();
                } else if (!Et1.equals("") && !Et2.equals("") && !Et4.equals("")) { //et1、et2、et4全不为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like(Et1),
                            TaskEntityDao.Properties.PeopleName.like(Et2), TaskEntityDao.Properties.BeiZhu.like(Et4)).list();
                }
                if (taskEntities.size() != 0) {
                    Collections.reverse(taskEntities);
                    data2Adapter = new Data2Adapter(context, taskEntities, DataFragment.this);
                    listView2.setAdapter(data2Adapter);
                } else {
                    Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                }
                // 隐藏软键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                break;
            case R.id.btn_quanBu://全部点击事件
                taskEntities = taskEntityDao.loadAll();
                //edittext全部置为""
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                if (taskEntities.size() != 0) {
                    Collections.reverse(taskEntities);//倒序
                    data2Adapter = new Data2Adapter(context, taskEntities, DataFragment.this);
                    listView2.setAdapter(data2Adapter);
                } else {
                    Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                }
                // 隐藏软键盘
                InputMethodManager imm1 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                break;
        }
    }

    //data1的删除按钮的点击事件
    public void showInitRes(QylEntity qylEntity, List<QylEntity> list) {
        qylEntities = list;
        qylEntityDao.delete(qylEntity);
        if (qylEntities.size() != 0) {
            //计算平均和累积
            String pingJun = "";//平均
            float leiJi = 0;//累积
            for (int i = 0; i < qylEntities.size(); i++) {
                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
            }
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
            tvLeiJiValue.setText(df2.format(leiJi));
        } else {
            tvPingJunValue.setText("--");
            tvLeiJiValue.setText("--");
        }
    }

    //data2的item的点击事件(点击下面的listview的每个item时，上面的listview更新数据)
    public void onItemClick(TaskEntity taskEntity) {
        qylEntities.clear();
        qylEntities = qylEntityDao.queryBuilder().where(QylEntityDao.Properties.Key.eq(taskEntity.getId())).list();
        if (qylEntities.size() != 0) {
            listview.setVisibility(View.VISIBLE);
            Collections.reverse(qylEntities);
            data1Adapter = new Data1Adapter(context, qylEntities, DataFragment.this);
            listview.setAdapter(data1Adapter);
            //计算平均和累积
            String pingJun = "";//平均
            float leiJi = 0;//累积
            for (int i = 0; i < qylEntities.size(); i++) {
                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
            }
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
            tvLeiJiValue.setText(df2.format(leiJi));
        } else {
            listview.setVisibility(View.INVISIBLE);
            tvPingJunValue.setText("--");
            tvLeiJiValue.setText("--");
            Toast.makeText(context, "本条没有数据！", Toast.LENGTH_SHORT).show();
        }

    }

    //长按点击事件
    public void onItemLongClick(final TaskEntity taskEntity) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("确认删除本条数据?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskEntityDao.delete(taskEntity);
                        taskEntities = taskEntityDao.loadAll();
                        if (taskEntities.size() != 0) {
                            listView2.setVisibility(View.VISIBLE);
                            Collections.reverse(taskEntities);
                            data2Adapter = new Data2Adapter(context, taskEntities, DataFragment.this);
                            listView2.setAdapter(data2Adapter);
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            //删完了把listview2隐藏
                            listView2.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 创建实例并显示
        normalDialog.show();
    }
}
