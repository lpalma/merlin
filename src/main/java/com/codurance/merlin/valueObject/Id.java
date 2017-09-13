package com.codurance.merlin.valueObject;

public class Id {
    private int value;

    public Id(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Id id = (Id) o;

        return value == id.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
