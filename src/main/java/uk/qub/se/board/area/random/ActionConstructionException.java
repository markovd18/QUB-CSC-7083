package uk.qub.se.board.area.random;

/**
 * @author <a href="mailto:">David Markov</a>
 * @since 10.04.22
 */
public class ActionConstructionException extends RuntimeException {
    public ActionConstructionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionConstructionException(String message) {
        super(message);
    }
}
