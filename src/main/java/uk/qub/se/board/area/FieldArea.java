package uk.qub.se.board.area;

import uk.qub.se.player.Player;

public class FieldArea implements Area {

    private AreaStage developmentStage = AreaStage.NOT_OWNED;

    private String name;

    private String catchPhrase;

    private AreaCosts costs;

    public FieldArea() {
    }

    public FieldArea(final String name, final String catchPhrase, final AreaCosts costs) throws IllegalStateException {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.costs = costs;
        this.validate();
    }

    @Override
    public void acceptPlayer(Player player) {

    }

    @Override
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("FieldArea name may not be null nor blank");
        }
        if (catchPhrase == null || name.isBlank()) {
            throw new IllegalStateException("Catch phrase may not be null nor blank");
        }

        costs.validate();
    }

    public static void registerToFactory() {
        //
    }

    public String getName() {
        return name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public AreaCosts getCosts() {
        return costs;
    }
}
