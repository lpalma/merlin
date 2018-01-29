package com.codurance.merlin.infrastructure;

import java.util.UUID;

public class UniqueIDGenerator {
    public String nextId() {
        return UUID.randomUUID().toString();
    }
}
