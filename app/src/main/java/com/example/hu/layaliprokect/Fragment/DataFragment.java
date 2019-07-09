package com.example.hu.layaliprokect.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import jxl.CellType;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
    private MenuInflater inflaters;

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
        Log.d("rrpp", "initView: ");
        setHasOptionsMenu(true);
    }

    public static DataFragment newInstance(int position) {
        DataFragment dataFragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        dataFragment.setArguments(bundle);
        return dataFragment;
    }

    //创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d("rrpp", "onCreateOptionsMenu: ");
        Log.d("rrpp", "onCreateOptionsMenu: ");
        inflater.inflate(R.menu.datamenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_xinxi:
                String str = "测试条数：" + taskEntityDao.loadAll().size();
                //弹框显示任务条数
                new AlertDialog.Builder(context).setMessage(str).create().show();
                break;
            case R.id.menu_baogao:
                updateExcel();//生成报告
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //生成报告
    private void updateExcel() {
        try {
            //先创建文件夹
            File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/拉压力综合测试仪/测试报告/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //再创建文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/拉压力综合测试仪/测试报告/" + "力测试数据.xls");
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/layali.xls");
            //读到模板文件
            Workbook wb = Workbook.getWorkbook(inputStream);
            //我们要操作的中间临时文件
            WritableWorkbook workbook = Workbook.createWorkbook(file, wb);
            WritableSheet sheet5 = workbook.getSheet(5);//力数据界面
            List<TaskEntity> taskList = taskEntityDao.loadAll();
            if (taskList.size() != 0) {
                Collections.reverse(taskList);//倒序，最后检的最先显示
                for (int i = 0; i < taskList.size(); i++) {
                    TaskEntity taskEntity = taskList.get(i);
                    Long id = taskEntity.getId();
                    List<QylEntity> qylList = qylEntityDao.queryBuilder().where(QylEntityDao.Properties.Key.eq(id)).list();
                    WritableCell cell_2y = sheet5.getWritableCell(2, i + 3);//受检单位
                    WritableCell cell_3y = sheet5.getWritableCell(3, i + 3);//测试人员
                    WritableCell cell_4y = sheet5.getWritableCell(4, i + 3);//测试时间
                    WritableCell cell_5y = sheet5.getWritableCell(5, i + 3);//平均力
                    WritableCell cell_6y = sheet5.getWritableCell(6, i + 3);//备注
                    if (cell_2y.getType() == CellType.LABEL && cell_3y.getType() == CellType.LABEL &&
                            cell_4y.getType() == CellType.LABEL && cell_5y.getType() == CellType.LABEL &&
                            cell_6y.getType() == CellType.LABEL) {
                        Label label2_y = (Label) cell_2y;
                        Label label3_y = (Label) cell_3y;
                        Label label4_y = (Label) cell_4y;
                        Label label5_y = (Label) cell_5y;
                        Label label6_y = (Label) cell_6y;

                        label2_y.setString(taskEntity.getUnitName());//受检单位
                        label3_y.setString(taskEntity.getPeopleName());//测试人员
                        label4_y.setString(taskEntity.getCreateTaskTime());//测试时间
                        if (qylList.size() != 0) {
                            //计算平均和累积
                            String pingJun = "";//平均
                            float leiJi = 0;//累积
                            for (int j = 0; j < qylList.size(); j++) {
                                leiJi = qylList.get(j).getCurrentLi() + leiJi;
                            }
                            pingJun = df2.format(leiJi / qylList.size());
                            label5_y.setString(pingJun);//平均力
                        } else {
                            label5_y.setString("-");//平均力
                        }
                        label6_y.setString(taskEntity.getBeiZhu());//备注
                    }
                }


            }
            if (inputStream != null) {
                inputStream.close();
            }
            workbook.write();
            workbook.close();
            wb.close();

            try {
                Intent intent = getWordFileIntent(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/拉压力综合测试仪/测试报告/" + "力测试数据.xls");
                startActivity(intent);
            } catch (Exception e) {
                Toasty.error(context, "打开WPS失败").show();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开WPS
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
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
