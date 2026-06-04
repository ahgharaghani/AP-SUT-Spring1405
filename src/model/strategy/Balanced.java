package model.strategy;

public class Balanced implements AttackStrategy {
    @Override
    public String getName() {
        return "balanced";
    }

    @Override
    public double getMultiplier() {
        return 1.0;
    }
}
