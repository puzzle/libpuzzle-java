package ch.puzzle.libpuzzle.demo.springboot.common;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;

import java.util.function.Predicate;

public class ApiActions<TEntity> extends CrudActions<TEntity, Long, Predicate<TEntity>> {
}
