package com.example.hu.layaliprokect.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.Interface.IGreate;
import com.example.hu.layaliprokect.R;
import com.example.hu.layaliprokect.TaskInfoHu.TaskInfo;
import com.example.hu.layaliprokect.Utils.EyesUtils;
import com.example.hu.layaliprokect.Utils.SharedPrefrenceUtils;
import com.example.hu.layaliprokect.Utils.TaskEntityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateTaskActivity extends AppCompatActivity implements IGreate {
    @BindView(R.id.et_1)
    public EditText et1;
    @BindView(R.id.iv_delete_1)
    ImageView ivDelete1;
    @BindView(R.id.tv1)
    public TextView tv1;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.et_3)
    public EditText et3;
    @BindView(R.id.iv_delete_3)
    ImageView ivDelete3;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbarlayout)
    AppBarLayout appbarlayout;
    @BindView(R.id.tv_begin)
    TextView tvBegin;
    //Edittext的输入内容
    private String et_input1;
    private String et_input3;

    private TaskInfo taskInfo;
    private Context context;
    private String className = "";
    private TaskEntity taskEntity;//总任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);
        context = this;

        if (Build.VERSION_CODES.M > Build.VERSION.SDK_INT) { //小于6.0
            EyesUtils.setStatusBarColorTw(this, ContextCompat.getColor(this, R.color.orange_my));    //设置标题栏透明白，字体为黑色
        } else {
            EyesUtils.setStatusBarLightMode(this, Color.WHITE);  //设置状态栏颜色和字体颜色
        }

        appbarlayoutStatus();
        //拿到上一次的参数
        et1.setText(SharedPrefrenceUtils.getUnitName(context));
        et3.setText(SharedPrefrenceUtils.getPeopleName(context));
        taskInfo = new TaskInfo(this);
        //Edittext获取焦点监听以及Edittext输入内容变化监听
        initView();
    }

    //Edittext获取焦点监听以及Edittext输入内容变化监听
    private void initView() {
        //EditText获取焦点事件监听
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    relativeLayout3.setVisibility(View.GONE);
                } else {
                    relativeLayout3.setVisibility(View.VISIBLE);
                }
            }
        });
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    relativeLayout1.setVisibility(View.GONE);
                } else {
                    relativeLayout1.setVisibility(View.VISIBLE);
                }
            }
        });

        //Edittext输入内容变化监听
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_input1 = et1.getText().toString().trim();
                int length = et_input1.length();//输入的长度
                tv1.setText(length + "/20");
                if (length == 20) {
                    Toast.makeText(CreateTaskActivity.this, "最多输入20个字符", Toast.LENGTH_SHORT).show();
                }
                if (!et_input1.equals("")) {
                    ivDelete1.setVisibility(View.VISIBLE);
                } else {
                    ivDelete1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_input3 = et3.getText().toString().trim();
                int length = et_input3.length();//输入的长度
                tv3.setText(length + "/20");
                if (length == 20) {
                    Toast.makeText(CreateTaskActivity.this, "最多输入20个字符", Toast.LENGTH_SHORT).show();
                }
                if (!et_input3.equals("")) {
                    ivDelete3.setVisibility(View.VISIBLE);
                } else {
                    ivDelete3.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //appBarLayout的打开与关闭状态
    private void appbarlayoutStatus() {
        collapsingToolbarLayout.setTitle("测试项目");
        // collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#fe8107"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        //添加返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.icon_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @OnClick({R.id.iv_delete_1, R.id.iv_delete_3, R.id.tv_begin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_delete_1://Edittext后删除图标的点击事件
                et1.setText("");
                break;
            case R.id.iv_delete_3:
                et3.setText("");
                break;
            case R.id.tv_begin:
                if (!et1.getText().toString().trim().equals("") &&
                        !et3.getText().toString().trim().equals("")) {
                    //开始时保存用户输入的手机单位和测试人员名
                    SharedPrefrenceUtils.setUnitName(context, et1.getText().toString().trim());
                    SharedPrefrenceUtils.setPeopleName(context, et3.getText().toString().trim());
                    Intent intent = new Intent();
                    intent.putExtra("unitName", et1.getText().toString().trim());
                    intent.putExtra("peopleName", et3.getText().toString().trim());
                    intent.setClass(context, TestActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "参数不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (MyApp.taskEntity == null) {
//                Log.i("kkk", "onKeyDown: taskEntity==null");
//                finish();
//                return true;
//            } else {
//                Log.i("kkk", "onKeyDown: taskEntity!=null");
//                Log.i("qqqqq", "MyApp.taskEntity.id=" + MyApp.taskEntity.getTaskId());
//                onBackClick(MyApp.taskEntity);
//                return true;
//            }
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }

//    private void onBackClick(final TaskEntity taskEntity) {
//        AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
//        normalDialog.setTitle("选择此任务状态");
//        normalDialog.setMessage("测试任务是否完成?");
//        normalDialog.setPositiveButton("已完成",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        taskEntity.setIsSave(true);
//                        TaskEntityUtils.update(taskEntity);
//                        MyApp.setTaskEntity(null);
//                        finish();
//                    }
//                });
//        normalDialog.setNegativeButton("未完成",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        taskEntity.setIsSave(false);
//                        TaskEntityUtils.update(taskEntity);
//                        MyApp.setTaskEntity(null);
//                        finish();
//                    }
//                });
//        // 创建实例并显示
//        normalDialog.show();
//    }


    @Override
    public void setParForHistoryTask(TaskEntity taskEntity) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //此界面被销毁时，将测试任务置空
        Log.i("kkk", "onDestroy: ");
        MyApp.setTaskEntity(null);
    }
}
