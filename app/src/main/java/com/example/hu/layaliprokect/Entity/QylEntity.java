package com.example.hu.layaliprokect.Entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class QylEntity {
    @Id(autoincrement = true)
    private Long id;//任务id
    private long key;//与taskEntity关联的key
    private float currentLi;//当前显示的力
    private float maxLi;//最大力
    private Integer testingNum;//序号
    private boolean isSave;
    private String qylCreateTime;//牵引力测试时的时间

    @Generated(hash = 920438615)
    public QylEntity(Long id, long key, float currentLi, float maxLi,
            Integer testingNum, boolean isSave, String qylCreateTime) {
        this.id = id;
        this.key = key;
        this.currentLi = currentLi;
        this.maxLi = maxLi;
        this.testingNum = testingNum;
        this.isSave = isSave;
        this.qylCreateTime = qylCreateTime;
    }
    @Generated(hash = 1959459023)
    public QylEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getKey() {
        return this.key;
    }
    public void setKey(long key) {
        this.key = key;
    }
    public float getCurrentLi() {
        return this.currentLi;
    }
    public void setCurrentLi(float currentLi) {
        this.currentLi = currentLi;
    }
    public float getMaxLi() {
        return this.maxLi;
    }
    public void setMaxLi(float maxLi) {
        this.maxLi = maxLi;
    }
    public Integer getTestingNum() {
        return this.testingNum;
    }
    public void setTestingNum(Integer testingNum) {
        this.testingNum = testingNum;
    }
    public boolean getIsSave() {
        return this.isSave;
    }
    public void setIsSave(boolean isSave) {
        this.isSave = isSave;
    }
    public String getQylCreateTime() {
        return this.qylCreateTime;
    }
    public void setQylCreateTime(String qylCreateTime) {
        this.qylCreateTime = qylCreateTime;
    }
}
