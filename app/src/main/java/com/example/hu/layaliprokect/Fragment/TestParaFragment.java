package com.example.hu.layaliprokect.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hu.layaliprokect.Application.MyApp;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.R;
import com.mchsdk.paysdk.mylibrary.ListFragment;

import butterknife.BindView;
import butterknife.OnClick;

//测试参数界面
public class TestParaFragment extends ListFragment {

    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.tv_checked_param)
    TextView tvCheckedParam;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_content1)
    LinearLayout llContent1;
    @BindView(R.id.tv_test_person)
    TextView tvTestPerson;
    @BindView(R.id.tv_item_result1)
    TextView tvItemResult1;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private String unitName;
    private String peopleName;

    //实例化自己，可以从外部传参
    public static TestParaFragment newInstance(String unitName, String peopleName) {
        TestParaFragment testParaFragment = new TestParaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("unitName", unitName);
        bundle.putString("peopleName", peopleName);
        testParaFragment.setArguments(bundle);
        return testParaFragment;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initView() {
        unitName = getArguments().getString("unitName");
        peopleName = getArguments().getString("peopleName");
        tvCheckedParam.setText(unitName);//受检单位
        tvNumber.setText(peopleName);//测试人员
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_test_param;
    }


    @OnClick({R.id.tv_task, R.id.tv_checked_param})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_task:
                Toast.makeText(getContext(), "哈撒给", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_checked_param:
                break;
        }
    }
}
