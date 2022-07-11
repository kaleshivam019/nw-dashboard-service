package com.ey.nwdashboard.model;

import java.util.List;

public class UserResponse {
    private List<UserModel> userList;

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
