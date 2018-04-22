package pl.xayan.tracker.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Package {
    enum Status {
        STATUS_NEW,
        STATUS_IN_TRANSPORT,
        STATUS_WAITING,
        STATUS_DELIVERED
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tracking_number")
    private String trackingNumber;

    private Status status;

    public int getId() {
        return id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
