package com.ey.nwdashboard.model;

import java.util.List;

public class UserModelResponse {
    private List<UserModel> userModelList;

    public List<UserModel> getUserModelList() {
        return userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        this.userModelList = userModelList;
    }
}
