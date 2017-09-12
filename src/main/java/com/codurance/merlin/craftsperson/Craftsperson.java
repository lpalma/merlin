package com.codurance.merlin.craftsperson;

import com.codurance.merlin.valueObjects.Id;
import com.codurance.merlin.valueObjects.Role;

public class Craftsperson {
    private String name;
    private Role role;
    private Id id;

    public Craftsperson(Id id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
