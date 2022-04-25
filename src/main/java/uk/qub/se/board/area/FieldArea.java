package uk.qub.se.board.area;

import uk.qub.se.board.area.factory.AreaFactory;
import uk.qub.se.player.Player;

import java.util.Scanner;

public class FieldArea implements DevelopableArea {

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
        System.out.printf("\nYou have landed on %s.\n%s\n", name, catchPhrase);
        if (isPlayerOwner(acceptedPlayer)) {
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
        final Investment initialCosts = costs.initialCost();
        System.out.println("\nThe area is not owned by any player.");
        System.out.printf("This area costs %d resources to acquire. You currently have %d resources.\n",
                initialCosts.resourceCost(), acceptedPlayer.getResources());

        if (playerDoesNotHaveEnoughResources(acceptedPlayer, initialCosts.resourceCost())) {
            System.out.println("You do not have enough resources to acquire this area.");
            return;
        }

        performDevelopmentProcess(acceptedPlayer, initialCosts, true);
    }

    private void performDevelopmentProcess(final Player investor, final Investment costs, final boolean isAcquisition) {
        final String processName = isAcquisition ? "acquisition" : "development";
        Scanner scanner = new Scanner(System.in);

        System.out.printf("By %s you receive %d investment points. Do you wish proceed?\n", processName, costs.investmentPointsReward());
        System.out.printf("Press 'Enter' to confirm the %s or anything else to decline: ", processName);
        final String acceptance = scanner.nextLine();
        if (!acceptance.isBlank()) {
            System.out.printf("\nArea %s declined.\n", processName);
            return;
        }

        investor.makeInvestment(costs);
        if (isAcquisition) {
            investor.addOwnedArea(this);
            owner = investor;
        }
        developmentStage = developmentStage.getNextStage();
        System.out.printf("\nArea %s completed. You gain %d investment points. You are left with %d resources and %d investment points.\n",
                processName, costs.investmentPointsReward(), investor.getResources(), investor.getInvestmentPoints());
        System.out.printf("Current area stage is %s.\n", developmentStage.name());
    }

    private boolean playerDoesNotHaveEnoughResources(final Player acceptedPlayer, final Integer resourceAmount) {
        return !acceptedPlayer.hasEnoughResources(resourceAmount);
    }

    private BoardMovementResult forcePlayerToInvest(final Player acceptedPlayer) {
        final Investment investmentCosts = costs.investmentCost();
        final int resourceCost = investmentCosts.resourceCost();
        System.out.printf("\nThe area is owned by %s. \nYou have to make an investment into their efforts to save the planet. Required investment is %d resources. \n",
                owner.getName(), resourceCost);
        if (playerDoesNotHaveEnoughResources(acceptedPlayer, resourceCost)) {
            System.out.println("\nYou do not have enough resources to invest. You declare bankruptcy. Game over.");
            return BoardMovementResult.PLAYER_GAME_OVER;
        }

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
        return acceptedPlayer != null && acceptedPlayer.equals(owner);
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

    @SuppressWarnings("unused")
    public String getCatchPhrase() {
        return catchPhrase;
    }

    @SuppressWarnings("unused")
    public AreaCosts getCosts() {
        return costs;
    }

    @Override
    public void developArea(final Player owner) {
        if (!isPlayerOwner(owner)) {
            System.out.println("Only owner of the area may develop it.");
            return;
        }

        final Investment costs = getCurrentDevelopmentCosts();
        if (costs == null) {
            System.out.println("Area cannot be developed any further.");
            return;
        }

        System.out.printf("This area costs %d resources to develop. You currently have %d resources.\n",
                costs.resourceCost(), owner.getResources());
        if (playerDoesNotHaveEnoughResources(owner, costs.resourceCost())) {
            System.out.println("You do not have enough resources to acquire this area.");
            return;
        }

        performDevelopmentProcess(owner, costs, false);
    }

    @Override
    public AreaStage getDevelopmentStage() {
        return developmentStage;
    }
}
