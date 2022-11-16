package com.ey.nwdashboard.model;

public class OauthLoginResponse {
    private String accessToken;
    private String accessTokenType;
    private String refreshToken;
    private Long accessTokenExpireIn;
    private String accessTokenScope;
    private String userRole;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenType() {
        return accessTokenType;
    }

    public void setAccessTokenType(String accessTokenType) {
        this.accessTokenType = accessTokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getAccessTokenExpireIn() {
        return accessTokenExpireIn;
    }

    public void setAccessTokenExpireIn(Long accessTokenExpireIn) {
        this.accessTokenExpireIn = accessTokenExpireIn;
    }

    public String getAccessTokenScope() {
        return accessTokenScope;
    }

    public void setAccessTokenScope(String accessTokenScope) {
        this.accessTokenScope = accessTokenScope;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
