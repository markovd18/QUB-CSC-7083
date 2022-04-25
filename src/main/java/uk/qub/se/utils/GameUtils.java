package uk.qub.se.utils;

import uk.qub.se.board.Board;
import uk.qub.se.board.Field;
import uk.qub.se.board.area.DevelopableArea;
import uk.qub.se.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameUtils {

    /**
     * Lists through all areas owned by {@code player}, checks whether he owns some {@link Field} entirely and
     * returns all owned areas that are part of those fields.
     *
     * This method is to be used by the {@code developArea} functionality.
     * @param player player whose areas to check
     * @return list of areas that may be developed
     */
    public static List<DevelopableArea> getDevelopableAreas(final Player player, final Board board) {
        if (player == null || board == null) {
            return null;
        }

        final Set<DevelopableArea> remainingAreas = new HashSet<>(player.getOwnedAreas());
        final Set<Field> fields = getParentFields(remainingAreas, board);

        final Map<DevelopableArea, DevelopmentCandidates> candidatesMap = createCandidatesMap(remainingAreas, fields, board);
        checkCandidates(remainingAreas, candidatesMap);

        return collectDevelopableAreas(candidatesMap);
    }

    private static List<DevelopableArea> collectDevelopableAreas(final Map<DevelopableArea, DevelopmentCandidates> candidatesMap) {
        final List<DevelopableArea> developableAreas = new LinkedList<>();
        for (DevelopmentCandidates candidates : getDistinctMapValues(candidatesMap)) {
            if (candidates.areAllFound()) {
                developableAreas.addAll(candidates.getFoundCandidates());
            }
        }

        return developableAreas;
    }

    private static Set<Field> getParentFields(final Set<DevelopableArea> areas, final Board board) {
        return areas.stream()
                .map(board::getParentField)
                .collect(Collectors.toSet());
    }

    private static Map<DevelopableArea, DevelopmentCandidates>
    createCandidatesMap(final Set<DevelopableArea> remainingAreas, final Set<Field> fields, final Board board) {
        final Map<DevelopableArea, DevelopmentCandidates> candidatesMap = new HashMap<>();
        final Set<DevelopableArea> tempRemainingAreas = new HashSet<>(remainingAreas);

        for (final Field field : fields) {
            addCandidates(candidatesMap, tempRemainingAreas, field, board);
        }

        return candidatesMap;
    }

    private static void addCandidates(final Map<DevelopableArea, DevelopmentCandidates> candidatesMap,
                               final Set<DevelopableArea> tempRemainingAreas,
                               final Field field, final Board board) {
        final List<DevelopableArea> fieldAreas = new ArrayList<>(field.getAreas());
        final var candidates = new DevelopmentCandidates(fieldAreas);

        final Iterator<DevelopableArea> it = tempRemainingAreas.iterator();
        while (it.hasNext()) {
            final DevelopableArea tempArea = it.next();
            if (board.getParentField(tempArea).equals(field)) {
                candidatesMap.put(tempArea, candidates);
                it.remove();
            }
        }
    }

    private static void checkCandidates(final Set<DevelopableArea> remainingAreas,
                                        final Map<DevelopableArea, DevelopmentCandidates> candidatesMap) {
        for (DevelopableArea remainingArea : remainingAreas) {
            candidatesMap.get(remainingArea).checkCandidate(remainingArea);
        }
    }

    private static  HashSet<DevelopmentCandidates>
    getDistinctMapValues(final Map<DevelopableArea, DevelopmentCandidates> candidatesMap) {
        return new HashSet<>(candidatesMap.values());
    }
}
