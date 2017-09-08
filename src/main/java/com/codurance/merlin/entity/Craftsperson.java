package com.codurance.merlin.entity;

public class Craftsperson {
    private String name;
    private String role;

    public Craftsperson(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String name() {
        return name;
    }

    public String role() {
        return role;
    }
}
