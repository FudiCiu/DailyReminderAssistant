package com.latihanandroid.dailyreminderassistant.model;

public class Setting {
    private boolean authentication;
    private boolean reminder;

    public Setting(boolean authentication, boolean reminder) {
        this.authentication = authentication;
        this.reminder = reminder;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}
