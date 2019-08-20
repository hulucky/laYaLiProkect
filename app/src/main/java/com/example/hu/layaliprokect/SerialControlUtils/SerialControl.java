package com.example.hu.layaliprokect.SerialControlUtils;

import android.content.Context;
import android.util.Log;

import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Beans.LaLiBean;
import com.example.hu.layaliprokect.Interface.ISensorInf;
import com.xzkydz.bean.ComBean;
import com.xzkydz.helper.SerialHelper;

import java.util.Arrays;

import javax.annotation.Nullable;

import static com.example.hu.layaliprokect.Application.MyApp.laLiBean;

public class SerialControl extends SerialHelper {
    public SerialControl(Context context, int mDataType) {
        super(context, mDataType);
    }

    public SerialControl(Context context, String sPort, String sBaudRate, int mDataType) {
        super(context, sPort, sBaudRate, mDataType);
    }

    @Override
    protected void onDataReceived(ComBean comBean) {
        if (comBean.recDataType == 64 || comBean.recDataType == 66) {
            if (comBean.recData.length == 22) {
                MyApp.comBeanLali = comBean;//给拉力实体类赋值
                if (laLiBean == null) {
                    laLiBean = new LaLiBean(comBean.recData);
                } else {
                    //解析拉力串口数据包，并且更新laLiBean的属性。
                    laLiBean.caculate(comBean.recData);
                    Log.i("bbb", "拉力数据长度为：" + comBean.recData.length +
                            "拉力数据为：" + Arrays.toString(comBean.recData));
                }
                // 设置回调接口
                if (receivedSensorData != null && laLiBean != null) {
                    receivedSensorData.getData(laLiBean);
                }
            }
        }
    }


    private OnReceivedSensorData receivedSensorData;

    public void setOnReceivedSensorListener(@Nullable OnReceivedSensorData l) {
        this.receivedSensorData = l;
    }

    public interface OnReceivedSensorData {
        void getData(ISensorInf sensorInf);
    }
}
