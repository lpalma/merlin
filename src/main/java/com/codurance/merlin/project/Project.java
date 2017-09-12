package com.codurance.merlin.project;

import com.codurance.merlin.valueObject.Id;

public class Project {
    private Id id;
    private String name;

    public Project(Id id, String name) {
        this.id = id;
        this.name = name;
    }
}
