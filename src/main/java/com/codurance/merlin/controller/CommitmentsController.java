package com.codurance.merlin.controller;

import com.codurance.merlin.entity.Commitment;
import com.codurance.merlin.repository.CommitmentRepository;
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
}
