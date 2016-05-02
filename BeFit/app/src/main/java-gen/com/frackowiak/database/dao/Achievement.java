package com.frackowiak.database.dao;

import com.frackowiak.database.db.DaoSession;
import de.greenrobot.dao.DaoException;

import com.frackowiak.database.db.AchievementDao;
import com.frackowiak.database.db.PlayerDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ACHIEVEMENT.
 */
public class Achievement {

    private Long id;
    private String Name;
    private String Description;
    private String Condition;
    private Boolean IsAchieved;
    private Long PlayerId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient AchievementDao myDao;

    private Player player;
    private Long player__resolvedKey;


    public Achievement() {
    }

    public Achievement(Long id) {
        this.id = id;
    }

    public Achievement(Long id, String Name, String Description, String Condition, Boolean IsAchieved, Long PlayerId) {
        this.id = id;
        this.Name = Name;
        this.Description = Description;
        this.Condition = Condition;
        this.IsAchieved = IsAchieved;
        this.PlayerId = PlayerId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAchievementDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String Condition) {
        this.Condition = Condition;
    }

    public Boolean getIsAchieved() {
        return IsAchieved;
    }

    public void setIsAchieved(Boolean IsAchieved) {
        this.IsAchieved = IsAchieved;
    }

    public Long getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(Long PlayerId) {
        this.PlayerId = PlayerId;
    }

    /** To-one relationship, resolved on first access. */
    public Player getPlayer() {
        Long __key = this.PlayerId;
        if (player__resolvedKey == null || !player__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayerDao targetDao = daoSession.getPlayerDao();
            Player playerNew = targetDao.load(__key);
            synchronized (this) {
                player = playerNew;
            	player__resolvedKey = __key;
            }
        }
        return player;
    }

    public void setPlayer(Player player) {
        synchronized (this) {
            this.player = player;
            PlayerId = player == null ? null : player.getId();
            player__resolvedKey = PlayerId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}