package uk.qub.se.board.area;

import uk.qub.se.player.Player;

public interface Area {

    String REGISTRAR_METHOD_NAME = "registerToFactory";

    BoardMovementResult acceptPlayer(Player player);

    void validate() throws IllegalStateException;

    String getName();
}
