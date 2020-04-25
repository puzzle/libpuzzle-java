package ch.puzzle.libpuzzle.springframework.boot.rest.actionfactory;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActionsPreset;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import lombok.RequiredArgsConstructor;

public abstract class CrudActionFactory<TEntity, TIdentifier, TFilter> {

    public abstract CreateAction<TEntity> create();

    public abstract FindAction<TIdentifier> find();

    public abstract ListAction<TFilter> list();

    public abstract UpdateAction<TIdentifier> update();

    public abstract DeleteAction<TIdentifier> delete();

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> readOnly() {
        return new ReadActionsPresetConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> writeOnly() {
        return new WriteActionsPresetConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> resourceOnly() {
        return new ResourceActionsPresetConfigurer<>(this);
    }

    public <TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> all() {
        return new AllActionsPresetConfigurer<>(this);
    }

    @RequiredArgsConstructor
    private static final class ReadActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> crudActionFactory;

        @Override
        public CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> preset) {
            return preset
                    .with(crudActionFactory.find())
                    .with(crudActionFactory.list());
        }
    }

    @RequiredArgsConstructor
    private static final class WriteActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> crudActionFactory;

        @Override
        public CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> preset) {
            return preset
                    .with(crudActionFactory.create())
                    .with(crudActionFactory.update())
                    .with(crudActionFactory.delete());
        }
    }

    @RequiredArgsConstructor
    private static final class ResourceActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> repositoryActions;

        @Override
        public CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> preset) {
            return preset
                    .with(repositoryActions.create())
                    .with(repositoryActions.find())
                    .with(repositoryActions.update())
                    .with(repositoryActions.delete());
        }
    }

    @RequiredArgsConstructor
    private static final class AllActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> implements CrudActionsPresetConfigurer<TEntity, TIdentifier, TFilter, TCrudActions> {

        private final CrudActionFactory<TEntity, TIdentifier, TFilter> repositoryActions;

        @Override
        public CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> configure(final CrudActionsPreset<TEntity, TIdentifier, TFilter, TCrudActions> preset) {
            return preset
                    .with(repositoryActions.create())
                    .with(repositoryActions.find())
                    .with(repositoryActions.list())
                    .with(repositoryActions.update())
                    .with(repositoryActions.delete());
        }
    }
}
