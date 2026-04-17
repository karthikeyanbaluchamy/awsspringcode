package com.sys.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {

    private String userName;

    private String address;

    public UserData() {
    }

    public UserData(String userName, String address) {
        this.userName = userName;
        this.address = address;
    }
}
