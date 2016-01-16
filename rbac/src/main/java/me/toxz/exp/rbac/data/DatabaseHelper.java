/*
 *     Copyright (C) 2016 Carlos
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package me.toxz.exp.rbac.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.MysqlDatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import me.toxz.exp.rbac.Role;
import me.toxz.exp.rbac.User;
import me.toxz.exp.rbac.data.model.AccessRecord;
import me.toxz.exp.rbac.data.model.MObject;
import me.toxz.exp.rbac.ura.CanAssign;
import me.toxz.exp.rbac.ura.CanRevoke;
import me.toxz.exp.rbac.ura.Condition;

import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by Carlos on 1/4/16.
 */
public class DatabaseHelper {
    public static final String URL = "jdbc:mysql://10.111.213.121/access_exp_rbac?user=root&password=9072";
    public static DatabaseType mDatabaseType;
    private static ConnectionSource mConnectionSource;
    private static Dao<MObject, Integer> mMObjectDao;
    private static Dao<AccessRecord, Integer> mAccessRecordDao;
    private static Dao<Role, Integer> mRoleDao;
    private static Dao<User, Integer> mUserDao;
    private static Dao<CanAssign, Integer> mCanAssignDao;
    private static Dao<CanRevoke, Integer> mCanRevokeDao;
    private static Dao<Condition, Integer> mConditionDao;

    public static <T> T callInTransaction(Callable<T> callable) throws SQLException {
        return TransactionManager.callInTransaction(mConnectionSource, callable);
    }

    private static void init() throws SQLException {
        mDatabaseType = new MysqlDatabaseType();
        mConnectionSource = new JdbcConnectionSource(URL, mDatabaseType);
    }

    public static Dao<AccessRecord, Integer> getAccessRecordDao() throws SQLException {
        if (mAccessRecordDao == null) {
            mAccessRecordDao = open(AccessRecord.class);
        }
        return mAccessRecordDao;
    }

    public static Dao<MObject, Integer> getMObjectDao() throws SQLException {
        if (mMObjectDao == null) {
            mMObjectDao = open(MObject.class);
        }
        return mMObjectDao;
    }

    public static Dao<Role, Integer> getRoleDao() throws SQLException {
        if (mRoleDao == null) {
            mRoleDao = open(Role.class);
        }
        return mRoleDao;
    }

    public static Dao<Condition, Integer> getConditionDao() throws SQLException {
        if (mConditionDao == null) {
            mConditionDao = open(Condition.class);
        }
        return mConditionDao;
    }

    public static Dao<User, Integer> getUserDao() throws SQLException {
        if (mUserDao == null) {
            mUserDao = open(User.class);
        }
        return mUserDao;
    }

    public static Dao<CanAssign, Integer> getCanAssignDao() throws SQLException {
        if (mCanAssignDao == null) {
            mCanAssignDao = open(CanAssign.class);
        }
        return mCanAssignDao;
    }

    public static Dao<CanRevoke, Integer> getCanRevokeDao() throws SQLException {
        if (mCanRevokeDao == null) {
            mCanRevokeDao = open(CanRevoke.class);
        }
        return mCanRevokeDao;
    }

    private static <T> Dao<T, Integer> open(Class<T> clazz) throws SQLException {
        init();
        return DaoManager.createDao(mConnectionSource, clazz);
    }
}
