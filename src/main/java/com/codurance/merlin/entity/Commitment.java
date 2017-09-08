package com.codurance.merlin.entity;

import java.time.LocalDate;

public class Commitment {
    private Craftsperson craftsperson;
    private Project project;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(Craftsperson craftsperson, Project project, LocalDate startDate, LocalDate endDate) {
        this.craftsperson = craftsperson;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Craftsperson craftsperson() {
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
