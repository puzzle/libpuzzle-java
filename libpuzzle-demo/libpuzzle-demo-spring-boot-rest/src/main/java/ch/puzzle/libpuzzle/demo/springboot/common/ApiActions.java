package ch.puzzle.libpuzzle.demo.springboot.common;

import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.*;

public class ApiActions<TEntity> extends CrudActions<
        ListAction<TEntity>,
        FindAction<TEntity, Long>,
        CreateAction<TEntity>,
        UpdateAction<TEntity, Long>,
        DeleteAction<Long>
        > {
}
