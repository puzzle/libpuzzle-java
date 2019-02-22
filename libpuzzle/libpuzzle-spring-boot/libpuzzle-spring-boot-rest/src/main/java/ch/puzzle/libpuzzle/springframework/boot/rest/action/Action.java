package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public class Action {

    public static <TAction> TAction unsupported() {
        throw new IllegalStateException();
    }
}
