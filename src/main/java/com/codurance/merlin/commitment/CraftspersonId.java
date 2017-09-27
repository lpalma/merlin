package com.codurance.merlin.commitment;

public class CraftspersonId {
    private String value;

    public CraftspersonId(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CraftspersonId that = (CraftspersonId) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public String asString() {
        return value;
    }
}
