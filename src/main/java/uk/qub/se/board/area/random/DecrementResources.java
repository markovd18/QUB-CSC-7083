package uk.qub.se.board.area.random;

import uk.qub.se.board.area.BoardMovementResult;
import uk.qub.se.player.Player;

public class DecrementResources implements RandomAction {

    public static final int AMOUNT_TO_DECREMENT = 50; //??

    @Override
    public BoardMovementResult initiate(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player to decrement resources to may not be null");
        }

        System.out.printf("\nYou have been accused of throwing thrash around in the local forest. You have to pay %d resources fine.\n", AMOUNT_TO_DECREMENT);
        if (playerHasToQuit(player)) {
            System.out.println("You do not have enough resources to pay the fine. You're going bankrupt. Game over...");
            return BoardMovementResult.PLAYER_GAME_OVER;
        }

        player.updateResourcesByAmount(- AMOUNT_TO_DECREMENT);
        System.out.printf("You are left with %d resources.\n", player.getResources());
        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    private boolean playerHasToQuit(Player player) {
        return (player.getResources() - AMOUNT_TO_DECREMENT) < 0;
    }
}
