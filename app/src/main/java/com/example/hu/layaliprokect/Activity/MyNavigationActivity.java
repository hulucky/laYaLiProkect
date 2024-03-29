package com.example.hu.layaliprokect.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.hu.layaliprokect.R;
import com.mchsdk.paysdk.mylibrary.BaseActivity;
import com.xzkydz.function.main.NavigationActivity;
import com.xzkydz.function.main.style.NavigationActivityManager;
import com.xzkydz.function.main.style.NavigationActivityManagerInf;
import com.xzkydz.function.pdf.ConstantPDF;
import com.xzkydz.function.pdf.PdfActivity;

//导航界面
public class MyNavigationActivity extends NavigationActivity {

    @Override
    public NavigationActivityManagerInf getNavigationActivityManager() {
        // 如果切图命名和库中使用的命名相同，引用库会直接替换切图，无需手动设置。
        // new 一个主界面管理者
        NavigationActivityManager navigationActivity = new NavigationActivityManager();
        //按键图标
        int bg[] = new int[]{R.mipmap.home_icon_cs, R.mipmap.home_icon_sj, R.mipmap.home_icon_tc, R.mipmap.home_icon_csbz, R.mipmap.home_icon_cgq
                , R.mipmap.home_icon_bg};
        navigationActivity
                .setMainBottomImageSrc(R.mipmap.home_pic_logo)
                //显示“配置”项
                .setShowAllConfig(false)
                //显示“配置-打印”项
                .setShowPrints(false)
                //显示“配置-配置”项
                .setShowConfiguration(false)
                //侧边栏顶部字体颜色
                .setLeftHeadTextColor(R.color.lib_black)
                //侧边栏每项Item字体颜色
                .setLeftItemTextColor(R.color.orange_my)
                //导航页背景
                .setNavigationActivityBg(R.mipmap.home_bg)
                //按键图标
                .setMainButtonBg(bg)
                .setNavigationViewHeardBg(R.mipmap.leftbar_top_bg)
                //App 名称
                .setAppName(R.string.app_name);
        return navigationActivity;
    }

    //测试
    @Override
    public void onTestButtonClickListener() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    //数据管理
    @Override
    public void onDataManagerClickListener() {
        Intent intent = new Intent(this, DataManagerActivity.class);
        startActivity(intent);
    }

    private long timeMillis;
    //查询报告
    @Override
    public void onQueryReportClicklistener() {
        if ((System.currentTimeMillis() - timeMillis) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            timeMillis = System.currentTimeMillis();
        } else {
            BaseActivity.finishAllActivity();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(com.xzkydz.function.mylibrary.R.id.btn_cgq).setVisibility(View.INVISIBLE);
        findViewById(com.xzkydz.function.mylibrary.R.id.btn_csbz).setVisibility(View.INVISIBLE);
        findViewById(com.xzkydz.function.mylibrary.R.id.btn_back).setVisibility(View.INVISIBLE);
    }


    //传感器
    @Override
    public void onSensorButtonClicklistener() {

    }

    //测试标准
    @Override
    public void OnTestStandarClicklistener() {
//        StartActivity.toActivity(this, StandardActivity.class);
//        Intent intent = new Intent(this, StandardActivity.class);
//        startActivity(intent);
    }

    // 返回显示 “产品信息”、“企业信息” 的 MarkdownActivity
    @NonNull
    @Override
    public Class<?> getMarkdownActivity() {
        return MyMarkdownActivity.class;
    }

    //说明书按钮点击事件，注意需要传入的两个参数写法
    @Override
    public void onSpecificationClickListenter() {
        super.onSpecificationClickListenter();
        Intent intent = new Intent(this, PdfActivity.class);
        intent.putExtra(ConstantPDF.nameFiles, "矿用无线拉压力测试仪说明书");
        intent.putExtra(ConstantPDF.urlFiles, "doc/矿用拉压力无线测试仪.pdf");
        startActivity(intent);
    }
}
