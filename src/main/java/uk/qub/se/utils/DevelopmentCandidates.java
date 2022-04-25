package uk.qub.se.utils;

import uk.qub.se.board.area.DevelopableArea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DevelopmentCandidates {

    private final Collection<DevelopableArea> candidates;
    private final Collection<DevelopableArea> foundCandidates;

    public DevelopmentCandidates(final Collection<DevelopableArea> candidates) {
        if (candidates == null) {
            throw new IllegalArgumentException("Candidates collection may not be null");
        }

        this.candidates = new ArrayList<>(candidates);
        foundCandidates = new ArrayList<>(candidates.size());
    }

    public boolean areAllFound() {
        return candidates.isEmpty();
    }

    public Collection<DevelopableArea> getFoundCandidates() {
        return foundCandidates;
    }

    public void checkCandidate(final DevelopableArea candidate) {
        final Iterator<DevelopableArea> it = candidates.iterator();
        while (it.hasNext()) {
            final DevelopableArea area = it.next();
            if (area.equals(candidate)) {
                it.remove();
                foundCandidates.add(area);
                return;
            }
        }
    }

}
