package com.example.vivianbabiryekulumba.poe.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Theme {
    private String theme;

    public Theme() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
