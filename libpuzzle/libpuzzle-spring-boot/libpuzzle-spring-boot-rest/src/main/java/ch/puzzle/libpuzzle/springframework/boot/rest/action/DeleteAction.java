package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteAction<TEntity, TEntityId, TDto> {

    private CrudRepository<TEntity, TEntityId> repository;

    public DeleteAction(CrudRepository<TEntity, TEntityId> repository) {
        this.repository = repository;
    }

    public ResponseEntity<TDto> execute(TEntityId id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
