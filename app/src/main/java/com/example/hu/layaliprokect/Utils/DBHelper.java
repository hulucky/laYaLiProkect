package com.example.hu.layaliprokect.Utils;

import android.content.Context;


import com.db.manager.DaoSession;
import com.db.manager.TaskEntityDao;
import com.example.hu.layaliprokect.Application.MyApp;

import java.util.List;

public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static DBHelper instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private TaskEntityDao taskEntityDao;

    // 单例模式，DBHelper只初始化一次
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = MyApp.getInstance().getmDaoSession();
            instance.taskEntityDao = instance.mDaoSession.getTaskEntityDao();
        }
        return instance;
    }
}