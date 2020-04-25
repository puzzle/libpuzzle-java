package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionExecution;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionExecution;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionExecution;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionExecution;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionExecution;
import org.springframework.beans.factory.annotation.Value;

public abstract class CrudActions<TEntity, TIdentifier, TFilter> extends ConfigurableCrudActions<TEntity, TIdentifier, TFilter> {

    @Value("${libpuzzle.rest.actions.list.defaults.offset:0}")
    private int defaultListOffset;

    @Value("${libpuzzle.rest.actions.list.defaults.limit:" + Integer.MAX_VALUE + "}")
    private int defaultListLimit;

    private CrudActionsConfig<TEntity, TIdentifier, TFilter, ?> config;

    public static <TEntity, TIdentifier, TFilter, TCrudActions extends CrudActions<TEntity, TIdentifier, TFilter>> CrudActionsConfig<TEntity, TIdentifier, TFilter, TCrudActions> configure(TCrudActions actions) {
        return new CrudActionsConfig<>(actions);
    }

    public CreateActionBuilder<TEntity, Object, Object> create() {
        return new CreateActionExecution<>(config.createAction);
    }

    public FindActionBuilder<TIdentifier, Object> find() {
        return new FindActionExecution<>(config.findAction);
    }

    public ListActionBuilder<TFilter, Object> list() {
        return new ListActionExecution<>(config.listAction, defaultListOffset, defaultListLimit);
    }

    public UpdateActionBuilder<TEntity, TIdentifier, Object, Object> update() {
        return new UpdateActionExecution<>(config.updateAction);
    }

    public DeleteActionBuilder<TIdentifier> delete() {
        return new DeleteActionExecution<>(config.deleteAction);
    }

    void apply(CrudActionsConfig<TEntity, TIdentifier, TFilter, ?> config) {
        if (null != this.config) {
            throw new IllegalStateException(String.format("Preset is already defined for %s.", this));
        }
        this.config = config;
    }
}
