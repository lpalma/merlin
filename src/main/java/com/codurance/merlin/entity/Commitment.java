package com.codurance.merlin.entity;

import java.time.LocalDate;

public class Commitment {
    private Employee employee;
    private Project project;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(Employee employee, Project project, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
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
