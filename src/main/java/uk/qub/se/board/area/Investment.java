package uk.qub.se.board.area;

import java.math.BigDecimal;

public record Investment(Integer resourceCost, Double investmentPointsReward) {
    public void validate() throws IllegalStateException {
        if (resourceCost == null || investmentPointsReward == null) {
            throw new IllegalStateException("Neither field resource cost nor investment points reward can be set to null.");
        }
        if (resourceCost <= 0 || investmentPointsReward <= 0) {
            throw new IllegalStateException("Both field resource cost and investment points reward must be positive numbers.");
        }
    }
}
