package pl.xayan.tracker.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

public interface BaseDao<T> {
    @Insert
    void insert(T... entities);

    @Update
    void update(T... entities);

    @Delete
    void delete(T... entities);
}
