package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public interface ActionFactory<TAction> {

    TAction create();

}
