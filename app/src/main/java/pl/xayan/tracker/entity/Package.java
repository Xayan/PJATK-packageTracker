package pl.xayan.tracker.entity;

public class Package {
    enum Status {
        STATUS_NEW,
        STATUS_IN_TRANSPORT,
        STATUS_WAITING,
        STATUS_DELIVERED
    }

    private int id;

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
