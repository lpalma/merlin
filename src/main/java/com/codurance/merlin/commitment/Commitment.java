package com.codurance.merlin.commitment;

import com.codurance.merlin.entity.Craftsperson;
import com.codurance.merlin.entity.Project;
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
}
