package model.strategy;

public class Aggressive implements AttackStrategy {
    @Override
    public String getName() {
        return "aggressive";
    }

    @Override
    public double getMultiplier() {
        return 1.5;
    }
}
