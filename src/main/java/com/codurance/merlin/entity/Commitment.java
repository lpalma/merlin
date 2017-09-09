package com.codurance.merlin.entity;

import com.codurance.merlin.valueObjects.Id;

import java.time.LocalDate;

public class Commitment {
    private Id id;
    private Employee employee;
    private Project project;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(Id id, Employee employee, Project project, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Id id() {
        return id;
    }

    public Employee employee() {
        return employee;
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
