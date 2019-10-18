package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory.CrudActionsPresetConfigurer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor
public final class CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter, TCrudActions>> {

    @With(AccessLevel.PRIVATE)
    final CreateAction<TEntity> createAction;

    @With(AccessLevel.PRIVATE)
    final FindAction<TIdentifier> findAction;

    @With(AccessLevel.PRIVATE)
    final ListAction<TFilter> listAction;

    @With(AccessLevel.PRIVATE)
    final UpdateAction<TIdentifier> updateAction;

    @With(AccessLevel.PRIVATE)
    final DeleteAction<TIdentifier> deleteAction;

    private final TCrudActions crudActions;

    CrudActionsPreset(final TCrudActions crudActions) {
        this(
                CreateAction.unsupported(),
                FindAction.unsupported(),
                ListAction.unsupported(),
                UpdateAction.unsupported(),
                DeleteAction.unsupported(),
                crudActions
        );
    }

    public TCrudActions apply() {
        crudActions.apply(this);
        return crudActions;
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final CreateAction<TEntity> action
    ) {
        return withCreateAction(action);
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final FindAction<TIdentifier> action
    ) {
        return withFindAction(action);
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final ListAction<TFilter> action
    ) {
        return withListAction(action);
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final UpdateAction<TIdentifier> action
    ) {
        return withUpdateAction(action);
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final DeleteAction<TIdentifier> action
    ) {
        return withDeleteAction(action);
    }

    public final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> with(
            CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> configurer
    ) {
        return configurer.configure(this);
    }
}
