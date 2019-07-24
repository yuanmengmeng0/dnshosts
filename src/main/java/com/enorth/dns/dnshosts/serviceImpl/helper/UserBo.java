package com.enorth.dns.dnshosts.serviceImpl.helper;

/**
 * Created by Administrator on 2017/5/18.
 */
public class UserBo {
    private long userId;
    private String userName;
    private String nickname;
    private int state;
    private String initDate;
    private String phoneNum;
    private String email;
    private String refreshToken;

    public UserBo() {
    }

    public UserBo(long userId, String userName, String nickname, int state, String initDate, String phoneNum, String email, String refreshToken) {
        this.userId = userId;
        this.userName = userName;
        this.nickname = nickname;
        this.state = state;
        this.initDate = initDate;
        this.phoneNum = phoneNum;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserBo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", state=" + state +
                ", initDate='" + initDate + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
