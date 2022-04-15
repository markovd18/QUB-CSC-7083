package uk.qub.se.utils;

public enum ExitCode {
    NOT_ENOUGH_PLAYERS(1),
    CONFIG_ERROR(2);

    private final int intValue;

    ExitCode(final int intValue) {
        this.intValue = intValue;
    }

    public int getValue() {
        return intValue;
    }
}
