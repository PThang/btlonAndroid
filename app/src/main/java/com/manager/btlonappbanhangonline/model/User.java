package com.manager.btlonappbanhangonline.model;

import java.io.Serializable;

public class User implements Serializable {
    String userId, userName,userPic;

    public User(String userId, String userName, String userPic) {
        this.userId = userId;
        this.userName = userName;
        this.userPic = userPic;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}
