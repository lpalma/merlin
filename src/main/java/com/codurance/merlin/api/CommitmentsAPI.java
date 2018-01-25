package com.codurance.merlin.api;

import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;
import com.codurance.merlin.service.CommitmentService;
import spark.Request;
import spark.Response;

import java.util.List;

public class CommitmentsAPI {

    public static final String ID = ":id";
    public static final int HTTP_NO_CONTENT = 204;
    private CommitmentService commitmentService;
    private CommitmentDataTransformer dataTransformer = new CommitmentDataTransformer();

    public CommitmentsAPI(CommitmentService commitmentService) {
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

    public Object delete(Request request, Response response) {
        CommitmentId commitmentId = new CommitmentId(request.params(ID));

        commitmentService.delete(commitmentId);

        response.status(HTTP_NO_CONTENT);

        return response;
    }
}
