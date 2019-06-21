package com.example.hu.layaliprokect.Entity;

import android.content.Context;

import com.example.hu.layaliprokect.Interface.ITaskInfo;
import com.xzkydz.function.search.module.ITaskRoot;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TaskEntity implements ITaskRoot {
    @Id(autoincrement = true)
    private Long id;//任务id
    private String unitName;//受检单位名称
    private String peopleName;//测试人员
    private String createTaskTime;//任务创建时间
    private int shuJuTiaoShu;//数据条数
    private boolean isTaskSave;//任务本身是否保存
    private boolean isQylSave;//牵引力是否保存
    private String beiZhu;//备注

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    @Generated(hash = 639318296)
    public TaskEntity(Long id, String unitName, String peopleName,
            String createTaskTime, int shuJuTiaoShu, boolean isTaskSave,
            boolean isQylSave, String beiZhu) {
        this.id = id;
        this.unitName = unitName;
        this.peopleName = peopleName;
        this.createTaskTime = createTaskTime;
        this.shuJuTiaoShu = shuJuTiaoShu;
        this.isTaskSave = isTaskSave;
        this.isQylSave = isQylSave;
        this.beiZhu = beiZhu;
    }

    @Override
    public Long getTaskId() {
        return id;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public String getTestFunction() {
        return null;
    }

    //单轨机编号
    @Override
    public String getNumber() {
        return null;
    }

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public int getTaskHaveGetData() {
        return shuJuTiaoShu;
    }

    @Override
    public boolean getTaskStatue() {
        return isTaskSave;
    }

    @Override
    public String getGreateTaskTime() {
        return createTaskTime;
    }



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getCreateTaskTime() {
        return this.createTaskTime;
    }

    public void setCreateTaskTime(String createTaskTime) {
        this.createTaskTime = createTaskTime;
    }

    public int getShuJuTiaoShu() {
        return this.shuJuTiaoShu;
    }

    public void setShuJuTiaoShu(int shuJuTiaoShu) {
        this.shuJuTiaoShu = shuJuTiaoShu;
    }

    public boolean getIsTaskSave() {
        return this.isTaskSave;
    }

    public void setIsTaskSave(boolean isTaskSave) {
        this.isTaskSave = isTaskSave;
    }

    public boolean getIsQylSave() {
        return this.isQylSave;
    }

    public void setIsQylSave(boolean isQylSave) {
        this.isQylSave = isQylSave;
    }

    public String getBeiZhu() {
        return this.beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }




   
}
