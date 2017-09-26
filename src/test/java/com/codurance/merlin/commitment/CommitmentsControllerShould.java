package com.codurance.merlin.commitment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitmentsControllerShould {

    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CommitmentRepository commitmentRepository;

    private CommitmentsController controller;

    @Before
    public void setUp() {
        controller = new CommitmentsController(commitmentRepository);
    }

    @Test
    public void return_all_commitments() throws Exception {
        List<Commitment> allCommitments = asList(aCommitment());

        when(commitmentRepository.all()).thenReturn(allCommitments);

        assertThat(controller.getAll(request, response), equalTo(allCommitments));
    }

    @Test
    public void add_new_commitment() {
        Commitment aCommitment = aCommitment();
        CommitmentData aCommitmentData = aCommitmentData();

        when(request.body()).thenReturn(aCommitmentJson());
        when(commitmentRepository.add(aCommitmentData)).thenReturn(aCommitment);

        Commitment commitment = controller.add(request, response);

        assertThat(aCommitmentData.equalTo(commitment), equalTo(true));
    }

    private Commitment aCommitment() {
        return new Commitment(
            new CommitmentId("1"),
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }

    private CommitmentData aCommitmentData() {
        return new CommitmentData(
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }

    private String aCommitmentJson() {
        return "{" +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";
    }
}