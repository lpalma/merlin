package com.codurance.merlin.infrastructure.commitment;

import com.codurance.merlin.commitment.Commitment;

public class CommitmentJson {
    private final String id;
    private final String craftspersonId;
    private final String projectId;
    private final String startDate;
    private final String endDate;

    public CommitmentJson(
        String id,
        String craftspersonId,
        String projectId,
        String startDate,
        String endDate) {

        this.id = id;
        this.craftspersonId = craftspersonId;
        this.projectId = projectId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommitmentJson that = (CommitmentJson) o;

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

    public static CommitmentJson fromCommitment(Commitment commitment) {
        return new CommitmentJson(
            commitment.id().asString(),
            commitment.craftspersonId().asString(),
            commitment.projectId().asString(),
            commitment.startDate().toString(),
            commitment.endDate().toString()
        );
    }
}
