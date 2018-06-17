package pl.xayan.tracker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import pl.xayan.tracker.db.dao.ParcelDao;
import pl.xayan.tracker.db.entity.Parcel;

@Database(entities = {Parcel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tracker";

    private static AppDatabase instance;

    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DATABASE_NAME
        )
                .fallbackToDestructiveMigration()
                .build();
    }

    public abstract ParcelDao parcelDao();
}
