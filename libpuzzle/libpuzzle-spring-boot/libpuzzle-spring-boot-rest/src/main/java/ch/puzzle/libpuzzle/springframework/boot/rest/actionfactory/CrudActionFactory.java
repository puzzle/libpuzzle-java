package ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActionsConfig;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import lombok.RequiredArgsConstructor;

public abstract class CrudActionFactory<TEntity, TIdentifier, TFilter> {

    public abstract CreateAction<TEntity> create();

    public abstract FindAction<TIdentifier, TEntity> find();

    public abstract ListAction<TEntity, TFilter> list();

    public abstract UpdateAction<TEntity, TIdentifier> update();

    public abstract DeleteAction<TIdentifier> delete();

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> readOnly() {
        return new ReadActionsConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> writeOnly() {
        return new WriteActionsConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> resourceOnly() {
        return new ResourceActionsConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> all() {
        return new AllActionsConfigurer<>(this);
    }

    @RequiredArgsConstructor
    private static final class ReadActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> crudActionFactory;

        @Override
        public CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> config) {
            return config
                    .with(crudActionFactory.find())
                    .with(crudActionFactory.list());
        }
    }

    @RequiredArgsConstructor
    private static final class WriteActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> crudActionFactory;

        @Override
        public CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> config) {
            return config
                    .with(crudActionFactory.create())
                    .with(crudActionFactory.update())
                    .with(crudActionFactory.delete());
        }
    }

    @RequiredArgsConstructor
    private static final class ResourceActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> repositoryActions;

        @Override
        public CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> config) {
            return config
                    .with(repositoryActions.create())
                    .with(repositoryActions.find())
                    .with(repositoryActions.update())
                    .with(repositoryActions.delete());
        }
    }

    @RequiredArgsConstructor
    private static final class AllActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> repositoryActions;

        @Override
        public CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> config) {
            return config
                    .with(repositoryActions.create())
                    .with(repositoryActions.find())
                    .with(repositoryActions.list())
                    .with(repositoryActions.update())
                    .with(repositoryActions.delete());
        }
    }
}
