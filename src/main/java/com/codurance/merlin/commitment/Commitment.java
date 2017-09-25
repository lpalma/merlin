package com.codurance.merlin.commitment;

import java.time.LocalDate;

public class Commitment {
    private CommitmentId id;
    private CraftspersonId craftspersonId;
    private ProjectId projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    public Commitment(CommitmentId id, CraftspersonId craftspersonId, ProjectId projectId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.craftspersonId = craftspersonId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CommitmentId id() {
        return id;
    }


    public CraftspersonId craftspersonId() {
        return craftspersonId;
    }

    public ProjectId projectId() {
        return projectId;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commitment that = (Commitment) o;

        if (!id.equals(that.id)) return false;
        if (!craftspersonId.equals(that.craftspersonId)) return false;
        if (!projectId.equals(that.projectId)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + craftspersonId.hashCode();
        result = 31 * result + projectId.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
