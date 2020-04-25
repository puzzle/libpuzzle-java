package ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActionsConfig;

public interface CrudActionsConfigurer<TEntity, TFilter, TIdentifier, TCrudActions extends CrudActions<TEntity, TFilter, TIdentifier>> {

    CrudActionsConfig<TEntity, TFilter, TIdentifier, TCrudActions> configure(CrudActionsConfig<TEntity, TFilter, TIdentifier, TCrudActions> config);
}
