package uk.qub.se.board.area;

import uk.qub.se.player.Player;

public interface DevelopableArea extends Area {

    void developArea(Player owner);

    AreaStage getDevelopmentStage();
}
