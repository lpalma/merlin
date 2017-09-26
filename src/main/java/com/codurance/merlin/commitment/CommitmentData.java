package com.codurance.merlin.commitment;

import java.time.LocalDate;

public class CommitmentData {
    private CraftspersonId craftspersonId;
    private ProjectId projectId;
    private LocalDate startDate;
    private LocalDate endDate;

    public CommitmentData(CraftspersonId craftspersonId, ProjectId projectId, LocalDate startDate, LocalDate endDate) {
        this.craftspersonId = craftspersonId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public boolean equalTo(Commitment commitment) {
        return this.equals(new CommitmentData(
                commitment.craftspersonId(),
                commitment.projectId(),
                commitment.startDate(),
                commitment.endDate()
        ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommitmentData that = (CommitmentData) o;

        if (!craftspersonId.equals(that.craftspersonId)) return false;
        if (!projectId.equals(that.projectId)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        int result = craftspersonId.hashCode();
        result = 31 * result + projectId.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
