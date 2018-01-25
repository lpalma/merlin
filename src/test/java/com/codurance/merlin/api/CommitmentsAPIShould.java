package com.codurance.merlin.api;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.CommitmentDataTransformer;
import com.codurance.merlin.infrastructure.commitment.CommitmentJson;
import com.codurance.merlin.service.CommitmentService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitmentsAPIShould {

    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";

    public static final String COMMITMENT_JSON = "{" +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";

    public static final int HTTP_CREATED = 201;
    public static final int HTTP_NO_CONTENT = 204;
    public static final String ID = ":id";
    public static final String COMMITMENT_ID = "commitmentId";

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CommitmentJson aCommitmentJson;

    @Mock
    private CommitmentService commitmentService;

    @Mock
    private Commitment commitment;

    @Mock
    private CommitmentDataTransformer dataTransformer;

    private CommitmentsAPI controller;

    @Before
    public void setUp() {
        controller = new CommitmentsAPI(commitmentService, dataTransformer);
    }

    @Test
    public void return_all_commitments() {
        List<CommitmentJson> commitments = asList(aCommitmentJson);

        when(commitmentService.all()).thenReturn(commitments);

        assertThat(controller.getAll(request, response)).isEqualTo(commitments);
    }

    @Test
    public void add_new_commitment() {
        CommitmentData commitmentData = aCommitmentData();

        when(request.body()).thenReturn(COMMITMENT_JSON);
        when(dataTransformer.fromJson(COMMITMENT_JSON)).thenReturn(commitmentData);
        when(commitmentService.add(commitmentData)).thenReturn(commitment);
        when(dataTransformer.jsonFor(commitment)).thenReturn(COMMITMENT_JSON);

        String commitment = controller.add(request, response);

        verify(response).status(HTTP_CREATED);
        assertThat(commitment).isEqualTo(COMMITMENT_JSON);
    }

    @Test
    public void delete_a_commitment() {
        CommitmentId commitmentId = new CommitmentId(COMMITMENT_ID);
        when(request.params(ID)).thenReturn(COMMITMENT_ID);

        controller.delete(request, response);

        verify(commitmentService).delete(commitmentId);
        verify(response).status(HTTP_NO_CONTENT);
    }


    private CommitmentData aCommitmentData() {
        return new CommitmentData(
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }

}