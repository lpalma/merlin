package com.codurance.merlin.commitment;

import com.codurance.merlin.craftsperson.Craftsperson;
import com.codurance.merlin.craftsperson.CraftspersonId;
import com.codurance.merlin.project.Project;
import com.codurance.merlin.project.ProjectId;
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

    public static final Craftsperson JOHN_SMITH = new Craftsperson(new CraftspersonId("1"), "John Smith");
    public static final Project PROJECT_ALPHA = new Project(new ProjectId("1"), "Alpha");

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

    private Commitment aCommitment() {
        return new Commitment(
            new CommitmentId("1"),
            JOHN_SMITH,
            PROJECT_ALPHA,
            LocalDate.of(2017, 10, 10),
            LocalDate.of(2017, 12, 10)
        );
    }
}