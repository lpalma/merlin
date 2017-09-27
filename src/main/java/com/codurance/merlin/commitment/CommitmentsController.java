package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class CommitmentsController {

    private CommitmentRepository commitments;

    public CommitmentsController(CommitmentRepository commitments) {
        this.commitments = commitments;
    }

    public List<CommitmentJson> getAll(Request request, Response response) {
        return commitments.all()
                .stream()
                .map(Commitment::asJson)
                .collect(Collectors.toList());
    }

    public Commitment add(Request request, Response response) {
        CommitmentDataTransformer transformer = new CommitmentDataTransformer();

        Commitment commitment = commitments.add(transformer.fromJson(request.body()));

        response.status(201);

        return commitment;
    }
}
