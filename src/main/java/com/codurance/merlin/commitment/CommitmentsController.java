package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
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

    public Commitment add(Request request, Response response) {
        CommitmentDataTransformer transformer = new CommitmentDataTransformer();

        Commitment commitment = commitments.add(transformer.fromJson(request.body()));

        response.status(201);

        return commitment;
    }
}
