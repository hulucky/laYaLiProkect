package com.example.hu.layaliprokect.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hu.layaliprokect.Adapter.TestAdapter;
import com.example.hu.layaliprokect.Fragment.DataFragment;
import com.example.hu.layaliprokect.Fragment.TestFragment;
import com.example.hu.layaliprokect.Fragment.TestParaFragment;
import com.example.hu.layaliprokect.R;
import com.example.hu.layaliprokect.Utils.EyesUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.jaeger.library.StatusBarUtil;
import com.xzkydz.function.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//牵引力测试界面
public class TestActivity extends AppCompatActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_tilte)
    TextView tvTilte;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.titleBar)
    LinearLayout titleBar;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp)
    CustomViewPager vp;

    private TestAdapter testAdapter;
    private List<String> titleList;
    private TestActivity instance;
    private List<Fragment> fragmentList;
    private String unitName;
    private String peopleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        this.setTitle("力的测试");

        if (Build.VERSION_CODES.M > Build.VERSION.SDK_INT) { //小于6.0
            EyesUtils.setStatusBarColorTw(this, ContextCompat.getColor(this, R.color.orange_my));    //设置标题栏透明白，字体为黑色
        } else {
            EyesUtils.setStatusBarLightMode(this, Color.WHITE);  //设置状态栏颜色和字体颜色
        }
        instance = this;
        unitName = getIntent().getStringExtra("unitName");
        peopleName = getIntent().getStringExtra("peopleName");
        initView();
        vp.setCurrentItem(1);
    }

    private void initView() {

        rlBack.setVisibility(View.VISIBLE);
        ivLeft.setVisibility(View.VISIBLE);
        tvLeft.setVisibility(View.VISIBLE);
        tvLeft.setText("力测试");

        //tablayout标题
        titleList = new ArrayList<>();
        titleList.add("测试参数");
        titleList.add("测试");
        titleList.add("数据");
        //fragment列表
        fragmentList = new ArrayList<>();
        //创建Fragment的时候就把受检单位名以及测试人员姓名传进去
        fragmentList.add(TestParaFragment.newInstance(unitName, peopleName));
        //创建Fragment的时候就把受检单位名以及测试人员姓名传进去
        fragmentList.add(TestFragment.newInstance(unitName, peopleName));
        fragmentList.add(DataFragment.newInstance(2));
        testAdapter = new TestAdapter(getSupportFragmentManager(), titleList, fragmentList, instance);
        //viewPager设置适配器
        vp.setAdapter(testAdapter);
        vp.setScanScroll(false);//禁止滑动
        //tablayout和viewpager关联
        tabLayout.setViewPager(vp);
    }


    @OnClick({R.id.iv_left, R.id.tv_tilte})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left://返回按钮
                finish();
//                onBackClick(MyApp.taskEntity);
                break;
            case R.id.tv_tilte:
                break;
        }
    }

}
