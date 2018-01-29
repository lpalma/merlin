package com.codurance.merlin.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UniqueIDGeneratorShould {

    private UniqueIDGenerator generator;

    @Before
    public void setup() {
        generator = new UniqueIDGenerator();
    }

    @Test
    public void generate_unique_ids() {
        String firstId = generator.nextId();
        String secondId = generator.nextId();

        assertThat(firstId).isNotEqualTo(secondId);
    }
}