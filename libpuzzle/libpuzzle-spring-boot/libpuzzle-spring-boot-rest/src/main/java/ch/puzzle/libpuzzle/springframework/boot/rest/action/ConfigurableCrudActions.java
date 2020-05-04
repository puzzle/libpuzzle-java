package ch.puzzle.libpuzzle.springframework.boot.rest.action;

public abstract class ConfigurableCrudActions<TEntity, TIdentifier, TFilter> {

    protected CrudActionsConfig<TEntity, TIdentifier, TFilter, ?> config;

    void apply(CrudActionsConfig<TEntity, TIdentifier, TFilter, ?> config) {
        if (null != this.config) {
            throw new IllegalStateException(String.format("Preset is already defined for %s.", this));
        }
        this.config = config;
    }
}
