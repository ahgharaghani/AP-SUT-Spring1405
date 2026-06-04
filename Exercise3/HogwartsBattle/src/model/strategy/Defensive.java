package model.strategy;

public class Defensive implements AttackStrategy {
    @Override
    public String getName() {
        return "defensive";
    }

    @Override
    public double getMultiplier() {
        return 0.7;
    }
}
