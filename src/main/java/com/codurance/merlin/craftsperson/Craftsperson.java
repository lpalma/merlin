package com.codurance.merlin.craftsperson;

public class Craftsperson {
    private String name;
    private CraftspersonId id;

    public Craftsperson(CraftspersonId id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Craftsperson that = (Craftsperson) o;

        if (!name.equals(that.name)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
