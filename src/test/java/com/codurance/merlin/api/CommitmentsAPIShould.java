package com.codurance.merlin.api;

import com.codurance.merlin.commitment.*;
import com.codurance.merlin.infrastructure.CommitmentJsonTransformer;
import com.codurance.merlin.commitment.CommitmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommitmentsAPIShould {

    public static final String CRAFTSPERSON_ID = "craftsperson1";
    public static final String PROJECT_ID = "project1";
    public static final String START_DATE = "2017-10-10";
    public static final String END_DATE = "2017-12-10";
    public static final String COMMITMENT_ID = "commitmentId";

    public static final String COMMITMENT_JSON = "{" +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";

    private static final String UPDATED_COMMITMENT_JSON = "{" +
            "\"id\": \"" + COMMITMENT_ID + "\"," +
            "\"craftspersonId\": \"" + CRAFTSPERSON_ID + "\"," +
            "\"projectId\": \"" + PROJECT_ID + "\"," +
            "\"startDate\": \"" + START_DATE + "\"," +
            "\"endDate\": \"" + END_DATE + "\"" +
            "}";

    private static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_NO_CONTENT = 204;
    public static final String ID = ":id";

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CommitmentService commitmentService;

    @Mock
    private Commitment commitment;

    @Mock
    private CommitmentJsonTransformer dataTransformer;

    private CommitmentsAPI api;

    @Before
    public void setUp() {
        api = new CommitmentsAPI(commitmentService, dataTransformer);
    }

    @Test
    public void return_all_commitments() {
        when(commitmentService.all()).thenReturn(singletonList(commitment));
        when(dataTransformer.jsonFor(commitment)).thenReturn(COMMITMENT_JSON);

        assertThat(api.getAll(request, response)).isEqualTo(singletonList(COMMITMENT_JSON));
    }

    @Test
    public void add_new_commitment() {
        CommitmentData commitmentData = aCommitmentData();

        when(request.body()).thenReturn(COMMITMENT_JSON);
        when(dataTransformer.fromJson(COMMITMENT_JSON)).thenReturn(commitmentData);
        when(commitmentService.add(commitmentData)).thenReturn(commitment);
        when(dataTransformer.jsonFor(commitment)).thenReturn(COMMITMENT_JSON);

        String commitment = api.add(request, response);

        verify(response).status(HTTP_CREATED);
        assertThat(commitment).isEqualTo(COMMITMENT_JSON);
    }

    @Test
    public void delete_a_commitment() {
        CommitmentId commitmentId = new CommitmentId(COMMITMENT_ID);
        when(request.params(ID)).thenReturn(COMMITMENT_ID);

        api.delete(request, response);

        verify(commitmentService).delete(commitmentId);
        verify(response).status(HTTP_NO_CONTENT);
    }

    @Test
    public void update_an_existing_commitment() {
        CommitmentData updatedCommitmentData = updatedCommitmentData();

        when(request.body()).thenReturn(UPDATED_COMMITMENT_JSON);
        when(dataTransformer.fromJson(UPDATED_COMMITMENT_JSON)).thenReturn(updatedCommitmentData);
        when(commitmentService.update(updatedCommitmentData)).thenReturn(of(commitment));
        when(dataTransformer.jsonFor(commitment)).thenReturn(UPDATED_COMMITMENT_JSON);

        String body = api.update(request, response);

        verify(response).status(HTTP_OK);
        assertThat(body).isEqualTo(UPDATED_COMMITMENT_JSON);
    }

    private CommitmentData updatedCommitmentData() {
        return new CommitmentData(
            new CommitmentId(COMMITMENT_ID),
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }

    private CommitmentData aCommitmentData() {
        return new CommitmentData(
            null,
            new CraftspersonId(CRAFTSPERSON_ID),
            new ProjectId(PROJECT_ID),
            LocalDate.parse(START_DATE),
            LocalDate.parse(END_DATE)
        );
    }
}