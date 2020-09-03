package ch.puzzle.libpuzzle.springframework.boot.rest.action.unsupported;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionParameters;
import org.springframework.http.ResponseEntity;

public class UnsupportedAction<TEntity, TFilter, TIdentifier> implements
        CreateAction<TEntity>,
        FindAction<TIdentifier, TEntity>,
        ListAction<TEntity, TFilter>,
        UpdateAction<TEntity, TIdentifier>,
        DeleteAction<TIdentifier> {

    @Override
    public TEntity execute(final CreateActionParameters<TEntity, ?> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TEntity execute(final FindActionParameters<TIdentifier> params) {
        return null;
    }

    @Override
    public Iterable<TEntity> execute(final ListActionParameters<TFilter> params) {
        return null;
    }

    @Override
    public TEntity execute(final UpdateActionParameters<TIdentifier, ?> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Void execute(final DeleteActionParameters<TIdentifier> params) {
        throw new UnsupportedOperationException();
    }
}
