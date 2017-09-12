package com.codurance.merlin.entity;

import com.codurance.merlin.valueObjects.Id;

import java.time.LocalDate;

public class Commitment {
    private Id id;
    private Craftsperson craftsperson;
    private Project project;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(Id id, Craftsperson craftsperson, Project project, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.craftsperson = craftsperson;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Id id() {
        return id;
    }

    public Craftsperson employee() {
        return craftsperson;
    }

    public Project project() {
        return project;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }
}
