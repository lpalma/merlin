package com.codurance.merlin.craftsperson;

import com.codurance.merlin.valueObject.Id;

public class Craftsperson {
    private String name;
    private Id id;

    public Craftsperson(Id id, String name) {
        this.id = id;
        this.name = name;
    }
}
