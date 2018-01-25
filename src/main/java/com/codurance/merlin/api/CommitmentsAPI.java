package com.codurance.merlin.api;

import com.codurance.merlin.commitment.Commitment;
import com.codurance.merlin.commitment.CommitmentData;
import com.codurance.merlin.commitment.CommitmentId;
import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.service.CommitmentService;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class CommitmentsAPI {

    public static final String ID = ":id";
    public static final int HTTP_NO_CONTENT = 204;
    private CommitmentService commitmentService;
    private CommitmentDataTransformer dataTransformer;

    public CommitmentsAPI(CommitmentService commitmentService, CommitmentDataTransformer dataTransformer) {
        this.commitmentService = commitmentService;
        this.dataTransformer = dataTransformer;
    }

    public List<String> getAll(Request request, Response response) {
        return commitmentService.all()
                .stream()
                .map(dataTransformer::jsonFor)
                .collect(Collectors.toList());
    }

    public String add(Request request, Response response) {
        CommitmentData commitmentData = dataTransformer.fromJson(request.body());
        Commitment commitment = commitmentService.add(commitmentData);

        response.status(201);

        return dataTransformer.jsonFor(commitment);
    }

    public Object delete(Request request, Response response) {
        CommitmentId commitmentId = new CommitmentId(request.params(ID));

        commitmentService.delete(commitmentId);

        response.status(HTTP_NO_CONTENT);

        return response;
    }
}
