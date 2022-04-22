package uk.qub.se.utils;

import uk.qub.se.board.area.Area;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DevelopmentCandidates {

    private final Collection<Area> candidates;
    private final Collection<Area> foundCandidates;

    public DevelopmentCandidates(final Collection<Area> candidates) {
        if (candidates == null) {
            throw new IllegalArgumentException("Candidates collection may not be null");
        }

        this.candidates = new ArrayList<>(candidates);
        foundCandidates = new ArrayList<>(candidates.size());
    }

    public boolean areAllFound() {
        return candidates.isEmpty();
    }

    public Collection<Area> getFoundCandidates() {
        return foundCandidates;
    }

    public void checkCandidate(final Area candidate) {
        final Iterator<Area> it = candidates.iterator();
        while (it.hasNext()) {
            final Area area = it.next();
            if (area.equals(candidate)) {
                it.remove();
                foundCandidates.add(area);
                return;
            }
        }
    }

}
