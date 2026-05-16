package models.abilities;

import models.Knight;

public abstract class Ability {
    protected final String name;
    protected final boolean isMultipleTarget;

    public Ability(String name, boolean isMultipleTarget) {
        this.name = name;
        this.isMultipleTarget = isMultipleTarget;
    }

    public String execute(Knight target) {
        throw new UnsupportedOperationException();
    }

    public String execute(Knight caster, Knight target) {
        throw new UnsupportedOperationException();
    }

    public String execute(Iterable<Knight> targets) {
        throw new UnsupportedOperationException();
    }

    public String execute(Knight caster, Iterable<Knight> targets) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return this.name;
    }
}