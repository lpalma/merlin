package com.codurance.merlin.commitment;

import com.codurance.merlin.project.Project;

import java.time.LocalDate;

public class Commitment {
    private CommitmentId id;
    private CraftspersonId craftsperson;
    private Project project;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(CommitmentId id, CraftspersonId craftspersonId, Project project, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.craftsperson = craftspersonId;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commitment that = (Commitment) o;

        if (!id.equals(that.id)) return false;
        if (!craftsperson.equals(that.craftsperson)) return false;
        if (!project.equals(that.project)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + craftsperson.hashCode();
        result = 31 * result + project.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
