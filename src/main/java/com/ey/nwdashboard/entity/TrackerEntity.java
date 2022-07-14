package com.ey.nwdashboard.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tracker", schema = "public")
public class TrackerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long trackerId;

    @Column(name = "gpn")
    private String trackerUserGPN;

    @Column(name = "vacation")
    private boolean vacation;

    @Column(name = "allowance")
    private boolean allowance;

    @Column(name = "shift")
    private boolean shift;

    @Column(name = "created_by")
    private String trackerCreatedBy;

    @Column(name = "created_on")
    private Timestamp trackerCreatedOn;

    @Column(name = "updated_by")
    private String trackerUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp trackerUpdatedOn;

    public Long getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(Long trackerId) {
        this.trackerId = trackerId;
    }

    public String getTrackerUserGPN() {
        return trackerUserGPN;
    }

    public void setTrackerUserGPN(String trackerUserGPN) {
        this.trackerUserGPN = trackerUserGPN;
    }

    public boolean isVacation() {
        return vacation;
    }

    public void setVacation(boolean vacation) {
        this.vacation = vacation;
    }

    public boolean isAllowance() {
        return allowance;
    }

    public void setAllowance(boolean allowance) {
        this.allowance = allowance;
    }

    public boolean isShift() {
        return shift;
    }

    public void setShift(boolean shift) {
        this.shift = shift;
    }

    public String getTrackerCreatedBy() {
        return trackerCreatedBy;
    }

    public void setTrackerCreatedBy(String trackerCreatedBy) {
        this.trackerCreatedBy = trackerCreatedBy;
    }

    public Timestamp getTrackerCreatedOn() {
        return trackerCreatedOn;
    }

    public void setTrackerCreatedOn(Timestamp trackerCreatedOn) {
        this.trackerCreatedOn = trackerCreatedOn;
    }

    public String getTrackerUpdatedBy() {
        return trackerUpdatedBy;
    }

    public void setTrackerUpdatedBy(String trackerUpdatedBy) {
        this.trackerUpdatedBy = trackerUpdatedBy;
    }

    public Timestamp getTrackerUpdatedOn() {
        return trackerUpdatedOn;
    }

    public void setTrackerUpdatedOn(Timestamp trackerUpdatedOn) {
        this.trackerUpdatedOn = trackerUpdatedOn;
    }
}
