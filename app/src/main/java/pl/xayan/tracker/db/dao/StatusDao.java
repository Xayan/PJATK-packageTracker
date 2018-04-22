package pl.xayan.tracker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pl.xayan.tracker.db.entity.Status;

@Dao
public interface StatusDao extends BaseDao<Status> {
    @Query("SELECT * FROM status")
    List<Status> getAll();

    @Query("SELECT * FROM status WHERE id = :id LIMIT 1")
    Status findById(int id);
}
