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
        FindAction<TIdentifier>,
        ListAction<TFilter>,
        UpdateAction<TIdentifier>,
        DeleteAction<TIdentifier> {

    @Override
    public <TResponseDto> ResponseEntity<TResponseDto> execute(final CreateActionParameters<TEntity, ?, TResponseDto> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TResponseDto> ResponseEntity<TResponseDto> execute(final FindActionParameters<TIdentifier, TResponseDto> params) {
        return null;
    }

    @Override
    public <TResponseDto> ResponseEntity<Iterable<TResponseDto>> execute(final ListActionParameters<TFilter, TResponseDto> params) {
        return null;
    }

    @Override
    public <TResponseDto> ResponseEntity<TResponseDto> execute(final UpdateActionParameters<TIdentifier, ?, TResponseDto> params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseEntity<Void> execute(final DeleteActionParameters<TIdentifier> params) {
        throw new UnsupportedOperationException();
    }
}
