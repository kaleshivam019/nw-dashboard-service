package com.ey.nwdashboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "user", schema = "public")
public class UserEntity {

    @Id
    @Column(name = "gpn")
    private String userGPN;

    @Column(name = "name")
    private String userName;

    @Column(name = "project")
    private String userProjectName;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "active")
    private boolean isUserActive;

    @Column(name = "created_by")
    private String userCreatedBy;

    @Column(name = "created_on")
    private Timestamp userCreatedOn;

    @Column(name = "updated_by")
    private String userUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp userUpdatedOn;

    public String getUserGPN() {
        return userGPN;
    }

    public void setUserGPN(String userGPN) {
        this.userGPN = userGPN;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProjectName() {
        return userProjectName;
    }

    public void setUserProjectName(String userProjectName) {
        this.userProjectName = userProjectName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isUserActive() {
        return isUserActive;
    }

    public void setUserActive(boolean userActive) {
        isUserActive = userActive;
    }

    public String getUserCreatedBy() {
        return userCreatedBy;
    }

    public void setUserCreatedBy(String userCreatedBy) {
        this.userCreatedBy = userCreatedBy;
    }

    public Timestamp getUserCreatedOn() {
        return userCreatedOn;
    }

    public void setUserCreatedOn(Timestamp userCreatedOn) {
        this.userCreatedOn = userCreatedOn;
    }

    public String getUserUpdatedBy() {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy(String userUpdatedBy) {
        this.userUpdatedBy = userUpdatedBy;
    }

    public Timestamp getUserUpdatedOn() {
        return userUpdatedOn;
    }

    public void setUserUpdatedOn(Timestamp userUpdatedOn) {
        this.userUpdatedOn = userUpdatedOn;
    }
}
