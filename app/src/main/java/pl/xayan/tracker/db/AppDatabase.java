package pl.xayan.tracker.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import pl.xayan.tracker.db.dao.EventDao;
import pl.xayan.tracker.db.dao.ParcelDao;
import pl.xayan.tracker.db.dao.StatusDao;
import pl.xayan.tracker.db.entity.Event;
import pl.xayan.tracker.db.entity.Parcel;
import pl.xayan.tracker.db.entity.Status;

@Database(entities = {Parcel.class, Status.class, Event.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tracker";

    private static AppDatabase instance;

    public abstract ParcelDao parcelDao();
    public abstract EventDao eventDao();
    public abstract StatusDao statusDao();

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
            .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            getInstance(context).statusDao().insert(Status.getPopulateData());
                        }
                    });
                }
            })
            .build();
    }
}
