package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

@SuppressWarnings("unused")
public interface ActionMatchers<TAction> {

    Class<? extends CrudActionConfigurer> configurer();

}


