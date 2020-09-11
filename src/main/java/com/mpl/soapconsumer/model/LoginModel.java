package com.mpl.soapconsumer.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Modelo de la solicitud
 */
public class LoginModel {
    @NotNull
    @Size(min=5)
    private String id;
    @NotNull
    @Size(min=5)
    private String pw;

    public LoginModel(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public LoginModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
