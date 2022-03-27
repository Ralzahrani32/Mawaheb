package com.example.mawaheb;

import java.io.Serializable;

public class Section implements Serializable {
    private String id;
    private String title;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
