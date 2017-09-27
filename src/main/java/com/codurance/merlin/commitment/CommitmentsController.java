package com.codurance.merlin.commitment;

import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;
import com.codurance.merlin.service.CommitmentService;
import spark.Request;
import spark.Response;

import java.util.List;

public class CommitmentsController {

    private CommitmentService commitmentService;
    private CommitmentDataTransformer dataTransformer = new CommitmentDataTransformer();

    public CommitmentsController(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    public List<CommitmentJson> getAll(Request request, Response response) {
        return commitmentService.all();
    }

    public CommitmentJson add(Request request, Response response) {
        CommitmentData commitmentData = dataTransformer.fromJson(request.body());
        CommitmentJson commitment = commitmentService.add(commitmentData);

        response.status(201);

        return commitment;
    }
}
