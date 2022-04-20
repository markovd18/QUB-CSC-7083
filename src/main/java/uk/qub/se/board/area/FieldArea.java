package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

import java.util.Scanner;

public class FieldArea implements Area {

    private AreaStage developmentStage = AreaStage.NOT_OWNED;

    private String name;

    private String catchPhrase;

    private AreaCosts costs;

    private Player owner;

    @SuppressWarnings("unused")
    public FieldArea() {
    }

    public FieldArea(final String name, final String catchPhrase, final AreaCosts costs) throws IllegalStateException {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.costs = costs;
        this.validate();
    }

    @Override
    public BoardMovementResult acceptPlayer(final Player acceptedPlayer) {
        if (acceptedPlayer == null) {
            return null;
        }

        return acceptValidPlayer(acceptedPlayer);
    }

    private BoardMovementResult acceptValidPlayer(final Player acceptedPlayer) {
        // TODO replace with UI API calls
        System.out.printf("You have landed on %s. %s\n", name, catchPhrase);
        if (isPlayerOwner(acceptedPlayer)) {
            // TODO replace with UI API calls
            System.out.println("This area is in your ownership. Welcome home.");
            return BoardMovementResult.NEXT_PLAYER_TURN;
        }

        if (isAreaOwned()) {
            return forcePlayerToInvest(acceptedPlayer);
        }

        proposeInvestmentOpportunity(acceptedPlayer);

        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    private void proposeInvestmentOpportunity(final Player acceptedPlayer) {
        // TODO this whole user communication to be replaced with injected UI object API calls
        final Investment initialCosts = costs.initialCost();
        System.out.println("The area is not owned by any player.");
        System.out.printf("This area costs %d resources to acquire. You have currently have %d resources.\n",
                initialCosts.resourceCost(), acceptedPlayer.getResources());

        if (playerDoesNotHaveEnoughResources(acceptedPlayer, initialCosts.resourceCost())) {
            System.out.println("You do not have enough resources to acquire this area.");
            return;
        }

        performDevelopmentProcess(acceptedPlayer, initialCosts);
    }

    private void performDevelopmentProcess(final Player investor, final Investment costs) {
        //TODO this is identical to what will be done in area (major) development - refactor in a way that it is reusable
        //TODO replace with UI API calls
        Scanner scanner = new Scanner(System.in);
        System.out.printf("By acquisition you receive %d investment points. Do you wish to acquire this area?\n", costs.investmentPointsReward());
        System.out.println("Press 'Enter' to acquire the area or anything else to decline: ");
        final String acceptance = scanner.nextLine();
        if (!acceptance.isBlank()) {
            System.out.println("Area acquisition declined.");
            return;
        }

        //TODO update global investment points state
        investor.makeInvestment(costs);
        investor.addOwnedArea(this);
        owner = investor;
        developmentStage = developmentStage.getNextStage();
        System.out.printf("Area acquired. You gain %d investment points. You are left with %d resources and %d investment points.\n",
                costs.investmentPointsReward(), investor.getResources(), investor.getInvestmentPoints());
    }

    private boolean playerDoesNotHaveEnoughResources(final Player acceptedPlayer, final Integer resourceAmount) {
        return !acceptedPlayer.hasEnoughResources(resourceAmount);
    }

    private BoardMovementResult forcePlayerToInvest(final Player acceptedPlayer) {
        final Investment investmentCosts = costs.investmentCost();
        final int resourceCost = investmentCosts.resourceCost();
        // TODO UI API
        System.out.printf("The area is owned by %s. You have to make an investment into their efforts to save the planet. Required investment is %d resources. \n",
                owner.getName(), resourceCost);
        if (playerDoesNotHaveEnoughResources(acceptedPlayer, resourceCost)) {
            System.out.println("You do not have enough resources to invest. You declare bankruptcy. Game over.");
            return BoardMovementResult.PLAYER_GAME_OVER;
        }

        //TODO update global investment points state
        acceptedPlayer.makeInvestment(investmentCosts);
        owner.updateResourcesByAmount(resourceCost);
        System.out.printf("Investment made. %d investment points acquired. You are left with %d resources and %d investment points.\n",
                investmentCosts.investmentPointsReward(), acceptedPlayer.getResources(), acceptedPlayer.getInvestmentPoints());
        return BoardMovementResult.NEXT_PLAYER_TURN;
    }

    @Override
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalStateException("FieldArea name may not be null nor blank");
        }
        if (catchPhrase == null || catchPhrase.isBlank()) {
            throw new IllegalStateException("Catch phrase may not be null nor blank");
        }
        if (costs == null) {
            throw new IllegalStateException("Area costs may not be null");
        }
    }

    @SuppressWarnings("unused")
    public static void registerToFactory(final AreaFactory factory) {
        //
    }

    private boolean isPlayerOwner(final Player acceptedPlayer) {
        return acceptedPlayer.equals(owner);
    }

    private Investment getCurrentDevelopmentCosts() {
        if (developmentStage.isHighestStage()) {
            return null;
        }

        if (isAreaNotOwned()) {
            return costs.initialCost();
        }

        if (developmentStage.isNextStageTheHighest()) {
            return costs.masterDevelopmentCost();
        }

        return costs.developmentCost();
    }

    private boolean isAreaNotOwned() {
        return developmentStage == AreaStage.NOT_OWNED;
    }

    private boolean isAreaOwned() {
        return owner != null;
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
