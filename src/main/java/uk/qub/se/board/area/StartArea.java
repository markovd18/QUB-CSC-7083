package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;
import uk.qub.se.utils.ValidationUtils;

public class StartArea implements Area {

    public static final String NAME = "Start area";
    public static final String JSON_ATTRIBUTE_NAME = "start";
    private Integer initialResources;
    private Integer regularGrant;

    @SuppressWarnings("unused")
    public StartArea() {
    }

    public StartArea(final Integer initialResources, final Integer regularGrant) throws IllegalStateException {
        this.initialResources = initialResources;
        this.regularGrant = regularGrant;
        this.validate();
    }

    @Override
    public BoardMovementResult acceptPlayer(final Player player) {
        if (player == null) {
            return null;
        }

        final int resourceAmount = getResourcesToAddToPlayer(player);
        player.updateResourcesByAmount(resourceAmount);

        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    private int getResourcesToAddToPlayer(final Player player) {
        if (playerJustStarted(player)) {
            return initialResources;
        }

        return regularGrant;
    }

    private boolean playerJustStarted(final Player player) {
        return player.getCurrentPosition() == null;
    }

    @Override
    public void validate() {
        if (initialResources == null || initialResources <= 0){
            throw new IllegalStateException("Start initial resources must be higher then 0.");
        }
        if (regularGrant != null && regularGrant < 0) {
            throw new IllegalStateException("Regular start grant may not be negative");
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("unused")
    public static void registerToFactory(final AreaFactory factory) {
        if (factory == null) {
            return;
        }

        factory.registerFactory(JSON_ATTRIBUTE_NAME, (json, mapper) -> {
            ValidationUtils.validateAreaFactoryMethodParams(json, mapper);

            final Area area = mapper.readValue(json, StartArea.class);
            area.validate();
            return area;
        });
    }

    @SuppressWarnings("unused")
    public Integer getInitialResources() {
        return initialResources;
    }

    @SuppressWarnings("unused")
    public Integer getRegularGrant() {
        return regularGrant;
    }
}
