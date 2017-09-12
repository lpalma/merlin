package com.codurance.merlin.entity;

import com.codurance.merlin.valueObjects.Id;

public class Project {
    private Id id;
    private String name;

    public Project(Id id, String name) {
        this.id = id;
        this.name = name;
    }
}
