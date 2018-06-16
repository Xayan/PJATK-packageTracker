package pl.xayan.tracker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.xayan.tracker.db.entity.Parcel;

@Dao
public interface ParcelDao extends BaseDao<Parcel> {
    @Query("SELECT * FROM Parcel ORDER BY id DESC")
    List<Parcel> getAll();

    @Query("SELECT * FROM Parcel WHERE id = :id LIMIT 1")
    Parcel findById(int id);
}
