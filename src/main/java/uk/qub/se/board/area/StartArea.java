package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

public class StartArea implements Area {

    private Integer initialResources;
    private Integer regularGrant;

    public StartArea() {
    }

    public StartArea(final Integer initialResources, final Integer regularGrant) throws IllegalStateException {
        this.initialResources = initialResources;
        this.regularGrant = regularGrant;
        this.validate();
    }

    @Override
    public void acceptPlayer(final Player player) {

    }

    @Override
    public void validate() {
        if (initialResources == null || initialResources <= 0){
            throw new IllegalStateException("Start initial resources must be higher then 0.");
        }
    }

    public static void registerToFactory() {
        AreaFactory.getInstance().registerFactory("start", (json, mapper) -> mapper.readValue(json, StartArea.class));
    }

    public Integer getInitialResources() {
        return initialResources;
    }

    public Integer getRegularGrant() {
        return regularGrant;
    }
}
