package com.codurance.merlin.controller;

import com.codurance.merlin.repository.CommitmentRepository;
import spark.Request;
import spark.Response;

public class CommitmentsController {

    private CommitmentRepository commitments;

    public CommitmentsController(CommitmentRepository commitments) {
        this.commitments = commitments;
    }

    public String getAll(Request request, Response response) {
        return commitments.all();
    }
}
