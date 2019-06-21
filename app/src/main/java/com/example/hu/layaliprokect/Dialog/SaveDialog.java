package com.example.hu.layaliprokect.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Entity.QylEntity;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.Fragment.TestFragment;
import com.example.hu.layaliprokect.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SaveDialog extends DialogFragment {
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private Context instance;
    private List<QylEntity> qylEntities;
    private String unitName;
    private String peopleName;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        instance = getActivity();
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(instance, R.style.BottomDialog);
        //隐藏Android默认的Title栏,设置Content前设定
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_save);
        dialog.setCanceledOnTouchOutside(true);//外部点击取消
//        // 设置宽度为屏宽, 靠近屏幕中部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;//宽度持平
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 高度持平
        window.setAttributes(lp);
        ButterKnife.bind(this, dialog); // Dialog即View
        initData();
        return dialog;
    }

    private void initData() {

    }

    //点击保存时把TestFragment中的数据源传进来
    public void setData(List<QylEntity> qylEntities, String unitName, String peopleName) {
        this.qylEntities = qylEntities;
        this.unitName = unitName;
        this.peopleName = peopleName;
    }

    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        TaskEntity taskEntity = new TaskEntity();
        //如果用户未输入备注，则保存备注为""
        if (!et.getText().toString().trim().equals("")) {
            taskEntity.setBeiZhu(et.getText().toString().trim());//保存备注
        } else {
            taskEntity.setBeiZhu("");//保存备注
        }
        taskEntity.setUnitName(unitName);//受检单位名称
        taskEntity.setPeopleName(peopleName);//测试人员
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time1 = sdf.format(new Date());
        taskEntity.setCreateTaskTime(time1);//测试时间
        //保存taskEntity
        MyApp.getInstance().getmDaoSession().getTaskEntityDao().insert(taskEntity);
        for (int i = 0; i < qylEntities.size(); i++) {
            QylEntity qylEntity = qylEntities.get(i);
            if (qylEntity.getIsSave()) {
                continue;
            }
            qylEntity.setIsSave(true);//任务置为已保存
            qylEntity.setKey(taskEntity.getId());//taskEntity和qylEntity关联起来
            //保存牵引力Entity
            MyApp.getInstance().getmDaoSession().getQylEntityDao().insert(qylEntity);
        }
        //点击保存后，listView要清零
        clearListView.clear();
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                "保存成功！", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);// 居中显示
        toast.show();
        dismiss();
    }

    public interface ClearListView {
        void clear();
    }

    public ClearListView clearListView;

    public void setOnClearListView(ClearListView clearListView) {
        this.clearListView = clearListView;
    }
}
