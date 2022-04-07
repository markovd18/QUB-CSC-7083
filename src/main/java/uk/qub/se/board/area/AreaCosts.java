package uk.qub.se.board.area;

public record AreaCosts(Investment initialCost, Investment developmentCost,
                        Investment masterDevelopmentCost, Investment investmentCost) {
    public void validate() throws IllegalStateException {
        initialCost.validate();
        developmentCost.validate();
        masterDevelopmentCost.validate();
        investmentCost.validate();
    }
}
