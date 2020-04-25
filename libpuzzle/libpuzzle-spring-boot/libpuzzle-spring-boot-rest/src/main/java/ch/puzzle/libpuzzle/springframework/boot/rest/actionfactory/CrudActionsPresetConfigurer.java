package ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActionsPreset;

public interface CrudActionsPresetConfigurer<TEntity, TFilter, TIdentifier, TCrudActions extends CrudActions<TEntity, TFilter, TIdentifier>> {

    CrudActionsPreset<TEntity, TFilter, TIdentifier, TCrudActions> configure(CrudActionsPreset<TEntity, TFilter, TIdentifier, TCrudActions> config);
}
