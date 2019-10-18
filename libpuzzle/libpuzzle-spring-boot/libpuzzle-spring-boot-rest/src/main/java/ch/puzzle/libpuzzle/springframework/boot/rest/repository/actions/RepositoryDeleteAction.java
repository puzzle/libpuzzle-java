package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class RepositoryDeleteAction<TIdentifier> implements DeleteAction<TIdentifier> {

    private final CrudRepository<?, TIdentifier> repository;

    @Override
    public ResponseEntity<Void> execute(final DeleteActionParameters<TIdentifier> params) {
        var identifier = params.identifier();
        if (!repository.existsById(identifier.get())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(identifier.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
