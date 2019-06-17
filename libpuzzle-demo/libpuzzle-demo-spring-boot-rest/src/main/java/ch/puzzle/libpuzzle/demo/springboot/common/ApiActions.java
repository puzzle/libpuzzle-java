package ch.puzzle.libpuzzle.demo.springboot.common;

import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.*;

public class ApiActions<TEntity> extends RestActions<
        ListAction<TEntity>,
        FindAction<TEntity, Long>,
        CreateAction<TEntity>,
        UpdateAction<TEntity, Long>,
        DeleteAction<Long>
        > {
}
