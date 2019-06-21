package com.example.hu.layaliprokect.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hu.layaliprokect.Adapter.NvQianYinLiAdapter;
import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Dialog.SaveDialog;
import com.example.hu.layaliprokect.Entity.QylEntity;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.Interface.ISensorInf;
import com.example.hu.layaliprokect.R;
import com.example.hu.layaliprokect.SerialControlUtils.SerialControl;
import com.example.hu.layaliprokect.Utils.MyFunc;
import com.example.hu.layaliprokect.Utils.TaskEntityUtils;
import com.mchsdk.paysdk.mylibrary.ListFragment;
import com.sensor.SensorData;
import com.sensor.SensorInf;
import com.sensor.view.SensorView;
import com.xzkydz.bean.ComBean;
import com.xzkydz.helper.ComControl;
import com.xzkydz.util.DataType;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

public class TestFragment extends ListFragment {
    @BindView(R.id.sv_laLi)
    SensorView svLaLi;
    @BindView(R.id.tv_li)
    TextView tvLi;
    @BindView(R.id.btn_clearLi)
    Button btnClearLi;
    @BindView(R.id.ll_li)
    LinearLayout llLi;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.tv_maxLi)
    TextView tvMaxLi;
    @BindView(R.id.btn_clearMaxLi)
    Button btnClearMaxLi;
    @BindView(R.id.ll_maxLi)
    LinearLayout llMaxLi;
    @BindView(R.id.lineup)
    View lineup;
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
    @BindView(R.id.line)
    View line;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.tv_leiJi)
    TextView tvLeiJi;
    @BindView(R.id.tv_leiJiValue)
    TextView tvLeiJiValue;
    @BindView(R.id.ll_leiJi)
    LinearLayout llLeiJi;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv_pingJun)
    TextView tvPingJun;
    @BindView(R.id.tv_pingJunValue)
    TextView tvPingJunValue;
    @BindView(R.id.ll_pingJun)
    LinearLayout llPingJun;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.fl_data)
    RelativeLayout flData;
    @BindView(R.id.tv_test)
    TextView tvTest;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_start)
    TextView tvStart;

    public SerialControl serialControl;
    private Context instance;
    private boolean isConnected;
    private MyApp myApp;
    //任务参数id
    private Long id_param;
    private DecimalFormat df = new DecimalFormat("0");
    private DecimalFormat df2 = new DecimalFormat("#0.00");
    private DecimalFormat df3 = new DecimalFormat("#0.000");
    public static int MSG_RECEIVED_DATA = 1;

    public List<Float> testResLists;//记录的拉力集合
    public List<TaskEntity> taskEntities;
    public List<QylEntity> qylEntities;
    private boolean isRunning = false;//
    public int testingNum = 0; //本次测试的序号
    public float maxFaceShow = 0f; //最大制动力
    private Handler handler;
    private byte[] buffer;
    private NvQianYinLiAdapter qylAdapter;
    private float record_zdzdl = 0;//记录的力的偏移量，校准用
    private float value;//传感器记录的值，在没有外力时，就是偏差
    private float currentRes;//实时力
    private String unitName;
    private String peopleName;

    @Override
    protected void lazyLoadData() {

    }

    //实例化自己，可以从外部传参
    public static TestFragment newInstance(String unitName, String peopleName) {
        TestFragment testFragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("unitName", unitName);
        bundle.putString("peopleName", peopleName);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    protected void initView() {
        myApp = MyApp.getInstance();
        unitName = getArguments().getString("unitName");
        peopleName = getArguments().getString("peopleName");
        instance = getActivity();
        serialControl = new SerialControl(instance, DataType.DATA_OK_PARSE);
        serialControl.setiDelay(20);
        //打开串口
        ComControl.OpenComPort(serialControl);
        svLaLi.setOnStatusChangeListener(new MyOnStatusChanger());
        //刷新传感器控件的状态
        serialControl.setOnReceivedSensorListener(new SerialControl.OnReceivedSensorData() {
            @Override
            public void getData(final ISensorInf sensorInf) {
                switch (sensorInf.getSensorType()) {
                    case 4://拉力
                        isConnected = true;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //sensorData 用于设置传感器信息（电量、信号、状态、图片）
                                SensorData sensorData = new SensorData(sensorInf.getPower(), sensorInf.getSignal(),
                                        SensorInf.NORMAL, System.currentTimeMillis());
                                svLaLi.setData(sensorData);
                            }
                        });
                        break;
                }
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!isRunning) {
                        btnClearMaxLi.setBackgroundResource(R.color.orange);
                        btnClearMaxLi.setClickable(true);
                    }
                } else {
                    btnClearMaxLi.setBackgroundResource(R.color.gray);
                    btnClearMaxLi.setClickable(false);
                }
            }
        });
        initData();

    }


    private void initData() {
        testResLists = new ArrayList<>();
        taskEntities = new ArrayList<>();
        qylEntities = new ArrayList<>();
        listview.setVisibility(View.VISIBLE);
        qylAdapter = new NvQianYinLiAdapter(getActivity(), qylEntities, TestFragment.this);
        listview.setAdapter(qylAdapter);
        //刚开始时就接受数据
        handler = new Handler();
        handler.post(runnable);
    }

    public void start() {//开始制动力测试
        testResLists.clear();//记录的拉力集合
        tvMaxLi.setText("0.000");
        maxFaceShow = 0.000f;//最大制动力
        currentRes = 0.000f;//实时力
        btnClearLi.setClickable(false);//开始后就不能再清零
        btnClearMaxLi.setClickable(false);//开始后就不能再清零
        btnClearLi.setBackgroundResource(R.color.gray);//把按钮置为灰色
        btnClearMaxLi.setBackgroundResource(R.color.gray);//把按钮置为灰色
        if (handler == null) {
            handler = new Handler();
            handler.post(runnable);
        }
    }

    public void save() {//保存
        if (qylEntities.size() != 0) {
            SaveDialog saveDialog = new SaveDialog();
            saveDialog.setData(qylEntities, unitName, peopleName);//把数据源传进去
            //保存后清空listView,并把平均和累计值置零
            saveDialog.setOnClearListView(new SaveDialog.ClearListView() {
                @Override
                public void clear() {
                    qylEntities.clear();
                    listview.setVisibility(View.INVISIBLE);
                    tvLeiJiValue.setText("--");
                    tvPingJunValue.setText("--");
                }
            });
            saveDialog.show(getFragmentManager(), "savedialog");
        } else {
            Toast.makeText(instance, "暂无数据保存", Toast.LENGTH_SHORT).show();
        }
    }

    public void record() {//记录
        QylEntity qylEntity = new QylEntity();
        //如果此时checkBox是处于选中状态，那么记录的是最大力
        if (checkbox.isChecked()) {
            btnClearLi.setClickable(true);//变为可点击
            btnClearMaxLi.setClickable(true);//变为可点击
            btnClearLi.setBackgroundResource(R.color.orange);
            btnClearMaxLi.setBackgroundResource(R.color.orange);
            qylEntity.setCurrentLi(Float.parseFloat(df2.format(maxFaceShow)));//保存最大力
        } else {//如果此时checkBox是处于未选中状态，那么记录的是实时力
            btnClearLi.setClickable(true);//变为可点击
            btnClearLi.setBackgroundResource(R.color.orange);
            qylEntity.setCurrentLi(Float.parseFloat(df2.format(currentRes)));//保存力
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        qylEntity.setQylCreateTime(time);//保存时间
        listview.setVisibility(View.VISIBLE);
        qylEntities.add(qylEntity);
        qylAdapter.updateListView(qylEntities);
        String pingJun = "";//平均
        float leiJi = 0;//累积
        for (int i = 0; i < qylEntities.size(); i++) {
            leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
        }
        pingJun = df2.format(leiJi / qylEntities.size());
        tvPingJunValue.setText(pingJun);
        tvLeiJiValue.setText(df2.format(leiJi));
        Toasty.success(getContext(), "成功记录一条测试数据", 2).show();
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                updateChart1(MyApp.comBeanLali);
            } catch (Exception e) {
                Log.i("vvv", "更新拉力异常exceprion");
            }
            //延迟一秒后又将该线程加入到线程队列中
            Message message = new Message();
            message.what = MSG_RECEIVED_DATA;
            if (handler != null) {
                handler.sendMessage(message);
            } else {
                return;
            }
            handler.postDelayed(runnable, 1000);
        }
    };


    private void updateChart1(ComBean comBeanLali) {
        if (comBeanLali.recData.length > 0) {
            buffer = comBeanLali.recData;//拿到串口数据
            int type = buffer[9] & 0xff;
            if (type == 64) {
                if (buffer.length == 22) {
//                    Log.i("ddd", "record_zdzdl(偏差)====== " + record_zdzdl);
                    float res0 = (float) MyFunc.twoBytesToInt(buffer, 16) / 100f;
                    float res1 = (float) MyFunc.twoBytesToInt(buffer, 14) / 100f;
                    Log.i("ddd", "数据为：" + Arrays.toString(buffer));
                    value = (res0 + res1) / 2f;//传感器记录的值，在没有外力时，就是偏差
//                    Log.i("ddd", "value（传感器的值）====== " + value);
                    //在校零时，record_zdzdl就是静止时的偏差,用读到的值减去偏差才是实际的值
                    currentRes = Math.abs(value - record_zdzdl);
                    tvLi.setText(df2.format(currentRes));
                    testResLists.add(Math.abs(currentRes));//拉力值集合
                    maxFaceShow = Math.abs(currentRes) > Math.abs(maxFaceShow) ? Math.abs(currentRes) : maxFaceShow;
                    if (testResLists.size() != 0) {
                        tvMaxLi.setText(df2.format(maxFaceShow));
                    }
                }
            } else {
                Toast.makeText(getContext(), "拿到的不是拉力传感器的数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "串口数据为空", Toast.LENGTH_SHORT).show();
        }
    }

    //点击删除按钮时，执行的事件
    public void showInitRes(QylEntity qylEntity, List<QylEntity> list) {
        this.qylEntities = list;
//        this.qylEntities = list;
        if (qylEntity.getIsSave()) {
            myApp.getmDaoSession().getQylEntityDao().delete(qylEntity);
        }
        //计算平均和累积值
        String pingJun = "";//平均
        float leiJi = 0;//累积
        for (int i = 0; i < qylEntities.size(); i++) {
            leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
        }
        if (qylEntities.size() != 0) {
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
        } else {
            tvPingJunValue.setText("0.000");
        }
        tvLeiJiValue.setText(df2.format(leiJi));
    }


    @OnClick({R.id.btn_clearLi, R.id.btn_clearMaxLi, R.id.tv_save, R.id.tv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clearLi://力 清零时，记录下此时的偏差
                record_zdzdl = value;//记录下此时要减去的值(偏差)
                tvLi.setText("0.000");
                Toasty.success(instance, "清除成功！", 2).show();
                break;
            case R.id.btn_clearMaxLi://最大力清零时，把最大力置为0，并更新textView即可
                maxFaceShow = 0.000f;
                tvMaxLi.setText("0.000");
                Toasty.success(instance, "清除成功！", 2).show();
                break;
            case R.id.tv_save:
                if (isConnected) {//传感器连接之后才可以开始
                    if (!isRunning) {//如果现在没在测试，那么可以保存
                        save();
                    } else {
                        Toast.makeText(getContext(), "请停止测试后再保存！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "传感器尚未连接，无法保存！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_start:
                if (isConnected) {//传感器连接之后才可以开始
                    if (isRunning) {//如果正在运行，那么此时的按钮显示为“记录”，那么点击之后变为开始
                        record();
                        tvStart.setText("开始");
                        isRunning = false;
                    } else {
                        start();
                        tvStart.setText("记录");
                        isRunning = true;
                    }
                } else {
                    Toast.makeText(getContext(), "传感器尚未连接，无法开始！", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    private class MyOnStatusChanger implements SensorView.OnStatusChangeListener {
        @Override
        public void status(View view, int i, int i1) {
            int id = view.getId();
            if (i1 == SensorInf.SEARCHING) {
                switch (id) {
                    case R.id.sv_laLi:
                        Toast.makeText(getContext(), "拉力传感器断开！", Toast.LENGTH_SHORT).show();
                        isConnected = false;
                        break;

                }
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_test;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        serialControl.close();
        svLaLi.destroy();
        if (handler != null) {
            handler.removeMessages(MSG_RECEIVED_DATA);
            handler.removeCallbacks(runnable);
            handler = null;
        }
        testingNum = 0;//退出时，把测试序号清零
    }
}
