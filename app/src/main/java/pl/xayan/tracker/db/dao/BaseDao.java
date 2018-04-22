package pl.xayan.tracker.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

public interface BaseDao<T> {
    @Insert
    void insert(T... statuses);

    @Update
    void update(T... statuses);

    @Delete
    void delete(T... statuses);
}
