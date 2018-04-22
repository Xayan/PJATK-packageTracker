package pl.xayan.tracker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import pl.xayan.tracker.db.entity.Event;

@Dao
public interface EventDao extends BaseDao<Event> {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE parcel_id = :parcelId")
    List<Event> findByParcel(int parcelId);
}
