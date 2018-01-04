package com.codurance.merlin.commitment;

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
public class CommitmentsControllerShould {

    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";
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

    private CommitmentsController controller;

    @Before
    public void setUp() {
        controller = new CommitmentsController(commitmentService);
    }

    @Test
    public void return_all_commitments() throws Exception {
        List<CommitmentJson> commitments = asList(aCommitmentJson);

        when(commitmentService.all()).thenReturn(commitments);

        assertThat(controller.getAll(request, response)).isEqualTo(commitments);
    }

    @Test
    public void add_new_commitment() {
        when(request.body()).thenReturn(commitmentAsJsonString());
        when(commitmentService.add(aCommitmentData())).thenReturn(aCommitmentJson);

        CommitmentJson commitment = controller.add(request, response);

        verify(response).status(HTTP_CREATED);
        assertThat(commitment).isEqualTo(aCommitmentJson);
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

    private String commitmentAsJsonString() {
        return "{" +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";
    }
}