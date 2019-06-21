package com.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.hu.layaliprokect.Entity.TaskEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TASK_ENTITY".
*/
public class TaskEntityDao extends AbstractDao<TaskEntity, Long> {

    public static final String TABLENAME = "TASK_ENTITY";

    /**
     * Properties of entity TaskEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UnitName = new Property(1, String.class, "unitName", false, "UNIT_NAME");
        public final static Property PeopleName = new Property(2, String.class, "peopleName", false, "PEOPLE_NAME");
        public final static Property CreateTaskTime = new Property(3, String.class, "createTaskTime", false, "CREATE_TASK_TIME");
        public final static Property ShuJuTiaoShu = new Property(4, int.class, "shuJuTiaoShu", false, "SHU_JU_TIAO_SHU");
        public final static Property IsTaskSave = new Property(5, boolean.class, "isTaskSave", false, "IS_TASK_SAVE");
        public final static Property IsQylSave = new Property(6, boolean.class, "isQylSave", false, "IS_QYL_SAVE");
        public final static Property BeiZhu = new Property(7, String.class, "beiZhu", false, "BEI_ZHU");
    }


    public TaskEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TaskEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TASK_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"UNIT_NAME\" TEXT," + // 1: unitName
                "\"PEOPLE_NAME\" TEXT," + // 2: peopleName
                "\"CREATE_TASK_TIME\" TEXT," + // 3: createTaskTime
                "\"SHU_JU_TIAO_SHU\" INTEGER NOT NULL ," + // 4: shuJuTiaoShu
                "\"IS_TASK_SAVE\" INTEGER NOT NULL ," + // 5: isTaskSave
                "\"IS_QYL_SAVE\" INTEGER NOT NULL ," + // 6: isQylSave
                "\"BEI_ZHU\" TEXT);"); // 7: beiZhu
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TASK_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TaskEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String unitName = entity.getUnitName();
        if (unitName != null) {
            stmt.bindString(2, unitName);
        }
 
        String peopleName = entity.getPeopleName();
        if (peopleName != null) {
            stmt.bindString(3, peopleName);
        }
 
        String createTaskTime = entity.getCreateTaskTime();
        if (createTaskTime != null) {
            stmt.bindString(4, createTaskTime);
        }
        stmt.bindLong(5, entity.getShuJuTiaoShu());
        stmt.bindLong(6, entity.getIsTaskSave() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsQylSave() ? 1L: 0L);
 
        String beiZhu = entity.getBeiZhu();
        if (beiZhu != null) {
            stmt.bindString(8, beiZhu);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TaskEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String unitName = entity.getUnitName();
        if (unitName != null) {
            stmt.bindString(2, unitName);
        }
 
        String peopleName = entity.getPeopleName();
        if (peopleName != null) {
            stmt.bindString(3, peopleName);
        }
 
        String createTaskTime = entity.getCreateTaskTime();
        if (createTaskTime != null) {
            stmt.bindString(4, createTaskTime);
        }
        stmt.bindLong(5, entity.getShuJuTiaoShu());
        stmt.bindLong(6, entity.getIsTaskSave() ? 1L: 0L);
        stmt.bindLong(7, entity.getIsQylSave() ? 1L: 0L);
 
        String beiZhu = entity.getBeiZhu();
        if (beiZhu != null) {
            stmt.bindString(8, beiZhu);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TaskEntity readEntity(Cursor cursor, int offset) {
        TaskEntity entity = new TaskEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // unitName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // peopleName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createTaskTime
            cursor.getInt(offset + 4), // shuJuTiaoShu
            cursor.getShort(offset + 5) != 0, // isTaskSave
            cursor.getShort(offset + 6) != 0, // isQylSave
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // beiZhu
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TaskEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUnitName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPeopleName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTaskTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setShuJuTiaoShu(cursor.getInt(offset + 4));
        entity.setIsTaskSave(cursor.getShort(offset + 5) != 0);
        entity.setIsQylSave(cursor.getShort(offset + 6) != 0);
        entity.setBeiZhu(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TaskEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TaskEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TaskEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}