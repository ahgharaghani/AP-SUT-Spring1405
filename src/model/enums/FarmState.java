package model.enums;

public enum FarmState {
    NORMAL_TURN,
    SYSTEM_VOTE,
    GOVERNOR_VOTE,
    CHOOSE_SHERIFF;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", " ");
    }
}
