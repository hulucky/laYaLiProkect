package com.example.hu.layaliprokect.Application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.db.manager.DaoMaster;
import com.db.manager.DaoSession;
import com.example.hu.layaliprokect.Beans.LaLiBean;
import com.example.hu.layaliprokect.Entity.TaskEntity;
import com.example.hu.layaliprokect.R;
import com.tencent.bugly.Bugly;
import com.xzkydz.bean.ComBean;
import com.xzkydz.function.app.KyApp;
import com.xzkydz.function.style.AppStyle;
import com.xzkydz.function.utils.SharedPreferencesUtils;

import org.json.JSONArray;

public class MyApp extends KyApp {
    public static final String MY_TAG = "MyApp";
    public static Long entityID;//单项测试任务的id（主键）

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static MyApp mInstance;


    public static TaskEntity taskEntity;//测试任务
    public static ComBean comBeanLali;//拉力（系统力）
    public static LaLiBean laLiBean;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = MyApp.this;
        setDataBase();
        Bugly.init(getApplicationContext(), "4ec8ce1fa9", false);
    }

    @Override
    public void setAppStyleColor() {
        super.setAppStyleColor();
        //设置APP的名称
        AppStyle.appNameId = R.string.app_name;
        //设置APP的主题色（Toolbar的颜色）
        AppStyle.appToolbarColor = R.color.orange_my;
        //实例化SharedPreferenceUtils工具类方便调用
        SharedPreferencesUtils.init(this);
    }

    //用于返回Application实例
    public static MyApp getInstance() {
        return mInstance;
    }

    private void setDataBase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处sport-db表示数据库名称 可以任意填写
        mHelper = new DaoMaster.DevOpenHelper(this, "layalidatabase", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public static TaskEntity getTaskEntity() {
        return taskEntity;
    }

    public static void setTaskEntity(TaskEntity taskEntity) {
        MyApp.taskEntity = taskEntity;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
