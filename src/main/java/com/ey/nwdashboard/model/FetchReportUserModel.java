package com.ey.nwdashboard.model;

public class FetchReportUserModel {
    private String userName;
    private FetchReportLeaveModel leaves;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FetchReportLeaveModel getLeaves() {
        return leaves;
    }

    public void setLeaves(FetchReportLeaveModel leaves) {
        this.leaves = leaves;
    }
}
