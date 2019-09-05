package com.iccm.zmmd.system.model;

/**
 * Created by Administrator on 2019/8/27.
 */
public class LoginParams {

    private String username;

    private String password;

    private String validateCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
}
