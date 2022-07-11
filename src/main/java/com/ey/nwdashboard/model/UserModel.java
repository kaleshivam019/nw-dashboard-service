package com.ey.nwdashboard.model;

import java.sql.Timestamp;

public class UserModel {

    private String userGPN;
    private String userName;
    private String userProjectName;
    private String userEmail;
    private boolean isUserActive;
    private String userCreatedBy;
    private Timestamp userCreatedOn;
    private String userUpdatedBy;
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
