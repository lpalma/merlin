package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.CommitmentDataJsonTransformer;
import spark.Request;
import spark.Response;

import java.util.List;

public class CommitmentsController {

    private CommitmentRepository commitments;

    public CommitmentsController(CommitmentRepository commitments) {
        this.commitments = commitments;
    }

    public List<Commitment> getAll(Request request, Response response) {
        return commitments.all();
    }

    public void add(Request request, Response response) {
        CommitmentDataJsonTransformer transformer = new CommitmentDataJsonTransformer();

        commitments.add(transformer.fromJson(request.body()));
    }
}
