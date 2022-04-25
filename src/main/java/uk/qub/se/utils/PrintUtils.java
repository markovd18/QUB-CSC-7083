package uk.qub.se.utils;

import uk.qub.se.board.area.Area;
import uk.qub.se.board.area.AreaStage;
import uk.qub.se.board.area.DevelopableArea;
import uk.qub.se.player.Player;

import java.util.List;
import java.util.Set;

public class PrintUtils {

    private static final String LINE_DIV = "----------------------------------------------------------------------";

    public static void printLineDiv() {
        System.out.println(LINE_DIV);
    }

    public static void printPlayerStats(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player to print out may not be null");
        }

        String name = player.getName();
        String pos = player.getCurrentPosition().getName();
        int invpts = player.getInvestmentPoints();
        int res = player.getResources();
        System.out.printf("|%-15s |%-35s |%-10d |%-10d |", name, pos, invpts, res);

        Set<DevelopableArea> playerOwnedAreas = player.getOwnedAreas();
        for (Area a : playerOwnedAreas) {
            String areaName = a.getName();
            System.out.printf("%-35s | \n|%-15s |%-35s |%-10s |%-10s |", areaName, "", "", "", "");
        }
        System.out.println();
    }

    public static void printGameInfoHeader(final int totalInvestmentPoints, final int maxInvestmentPoints) {
        System.out.printf("\nTotal number of investment points: %d / %d\n", totalInvestmentPoints, maxInvestmentPoints);
        printLineDiv();
        System.out.printf ("|%-15s |%-35s |%-10s |%-10s |%-35s\n",
                "PLAYER NAME", "CURRENT POS", "INV POINTS", "RESOURCES", "OWNED AREAS");
        printLineDiv();
    }

    public static void printMenuOptions(final Player currentPlayer, final List<DevelopableArea> developableAreas) {
        if (currentPlayer == null || developableAreas == null) {
            throw new IllegalArgumentException("Current player and developable areas may not be null to print out");
        }

        System.out.println( "\n " + currentPlayer.getName() + " Enter your next move:");
        System.out.println("--------------------------------");
        System.out.println("1. Display Stats");
        System.out.println("2. Roll Dice");
        System.out.println("3. Quit Game");
        if (!developableAreas.isEmpty()) {
            System.out.println("4. Develop area");
        }
    }

    public static void printDevelopableAreasList(final List<DevelopableArea> developableAreas) {
        if (developableAreas == null) {
            throw new IllegalArgumentException("Developable areas to print may not be null");
        }

        int areaIndex = 1;
        for (final DevelopableArea area : developableAreas) {
            final String tooltip = getDevelopableAreaTooltip(area.getDevelopmentStage());

            System.out.println(areaIndex + ". " + area.getName() + tooltip);
            areaIndex++;
        }
    }

    private static String getDevelopableAreaTooltip(final AreaStage areaStage) {
        return areaStage.isNextStageTheHighest()
                ? "(Major development)"
                : "(to stage " + areaStage.getNextStage().name() + ")";
    }
}
