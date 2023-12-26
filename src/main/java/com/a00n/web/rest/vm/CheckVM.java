package com.a00n.web.rest.vm;

public class CheckVM {

    private String key;
    private String email;

    public CheckVM(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CheckVM [key=" + key + ", email=" + email + "]";
    }
}
