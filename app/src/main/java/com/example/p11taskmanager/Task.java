package com.example.p11taskmanager;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String description;
    private String name;

    public Task(int id, String description, String name) {
        this.id = id;
        this.description = description;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (id + 1) + " " + name + "\n" + description;
    }
}