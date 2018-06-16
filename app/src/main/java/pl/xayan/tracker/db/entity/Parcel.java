package pl.xayan.tracker.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Parcel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "tracking_number")
    private String trackingNumber;

    @ColumnInfo(name = "label")
    private String label;

    @ColumnInfo(name = "aftership_id")
    private int aftershipId;

    @ColumnInfo(name = "aftershipSlug")
    private String aftershipSlug;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getAftershipId() {
        return aftershipId;
    }

    public void setAftershipId(int aftershipId) {
        this.aftershipId = aftershipId;
    }

    public String getAftershipSlug() {
        return aftershipSlug;
    }

    public void setAftershipSlug(String aftershipSlug) {
        this.aftershipSlug = aftershipSlug;
    }
}
