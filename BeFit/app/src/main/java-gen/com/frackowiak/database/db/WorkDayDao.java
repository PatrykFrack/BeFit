package com.frackowiak.database.db;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.frackowiak.database.dao.Day;
import com.frackowiak.database.dao.Player;

import com.frackowiak.database.dao.WorkDay;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table WORK_DAY.
*/
public class WorkDayDao extends AbstractDao<WorkDay, Long> {

    public static final String TABLENAME = "WORK_DAY";

    /**
     * Properties of entity WorkDay.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "Name", false, "NAME");
        public final static Property Date = new Property(2, java.util.Date.class, "Date", false, "DATE");
        public final static Property PlayerId = new Property(3, Long.class, "PlayerId", false, "PLAYER_ID");
        public final static Property DayId = new Property(4, Long.class, "DayId", false, "DAY_ID");
    };

    private DaoSession daoSession;


    public WorkDayDao(DaoConfig config) {
        super(config);
    }
    
    public WorkDayDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'WORK_DAY' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: Name
                "'DATE' INTEGER," + // 2: Date
                "'PLAYER_ID' INTEGER," + // 3: PlayerId
                "'DAY_ID' INTEGER);"); // 4: DayId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'WORK_DAY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, WorkDay entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String Name = entity.getName();
        if (Name != null) {
            stmt.bindString(2, Name);
        }
 
        java.util.Date Date = entity.getDate();
        if (Date != null) {
            stmt.bindLong(3, Date.getTime());
        }
 
        Long PlayerId = entity.getPlayerId();
        if (PlayerId != null) {
            stmt.bindLong(4, PlayerId);
        }
 
        Long DayId = entity.getDayId();
        if (DayId != null) {
            stmt.bindLong(5, DayId);
        }
    }

    @Override
    protected void attachEntity(WorkDay entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public WorkDay readEntity(Cursor cursor, int offset) {
        WorkDay entity = new WorkDay( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // Name
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // Date
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // PlayerId
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4) // DayId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, WorkDay entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setPlayerId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setDayId(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(WorkDay entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(WorkDay entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getPlayerDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getDayDao().getAllColumns());
            builder.append(" FROM WORK_DAY T");
            builder.append(" LEFT JOIN PLAYER T0 ON T.'PLAYER_ID'=T0.'_id'");
            builder.append(" LEFT JOIN DAY T1 ON T.'DAY_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected WorkDay loadCurrentDeep(Cursor cursor, boolean lock) {
        WorkDay entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Player player = loadCurrentOther(daoSession.getPlayerDao(), cursor, offset);
        entity.setPlayer(player);
        offset += daoSession.getPlayerDao().getAllColumns().length;

        Day day = loadCurrentOther(daoSession.getDayDao(), cursor, offset);
        entity.setDay(day);

        return entity;    
    }

    public WorkDay loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<WorkDay> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<WorkDay> list = new ArrayList<WorkDay>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<WorkDay> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<WorkDay> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
