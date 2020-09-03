package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory.CrudActionsConfigurer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@AllArgsConstructor
public final class CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> {

    @Getter
    @With(AccessLevel.PRIVATE)
    final CreateAction<TEntity> createAction;

    @Getter
    @With(AccessLevel.PRIVATE)
    final FindAction<TIdentifier, TEntity> findAction;

    @Getter
    @With(AccessLevel.PRIVATE)
    final ListAction<TEntity, TFilter> listAction;

    @Getter
    @With(AccessLevel.PRIVATE)
    final UpdateAction<TEntity, TIdentifier> updateAction;

    @Getter
    @With(AccessLevel.PRIVATE)
    final DeleteAction<TIdentifier> deleteAction;

    private final TCrudActions crudActions;

    CrudActionsConfig(final TCrudActions crudActions) {
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

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final CreateAction<TEntity> action
    ) {
        return withCreateAction(action);
    }

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final FindAction<TIdentifier, TEntity> action
    ) {
        return withFindAction(action);
    }

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final ListAction<TEntity, TFilter> action
    ) {
        return withListAction(action);
    }

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final UpdateAction<TEntity, TIdentifier> action
    ) {
        return withUpdateAction(action);
    }

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            final DeleteAction<TIdentifier> action
    ) {
        return withDeleteAction(action);
    }

    public final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> with(
            CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> configurer
    ) {
        return configurer.configure(this);
    }
}
