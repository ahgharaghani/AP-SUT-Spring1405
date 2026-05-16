package models.abilities;

import models.Knight;

public class Stun extends Ability{

    public Stun(String name, boolean isMultipleTarget) {
        super(name, isMultipleTarget);
    }

    @Override
    public String execute(Knight target) {
        target.stun();
        return target.getStringName() + " got stunned";
    }
}
