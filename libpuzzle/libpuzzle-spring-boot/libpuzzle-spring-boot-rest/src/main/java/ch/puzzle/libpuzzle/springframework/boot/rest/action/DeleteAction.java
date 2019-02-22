package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class DeleteAction<TEntity, TEntityId> {

    private CrudRepository<TEntity, TEntityId> repository;

    private Supplier<TEntityId> idSupplier = IllegalActionParam.missingParam(DeleteAction.class, "by");

    public DeleteAction(CrudRepository<TEntity, TEntityId> repository) {
        this.repository = repository;
    }

    public DeleteAction<TEntity, TEntityId> by(TEntityId id) {
        idSupplier = () -> id;
        return this;
    }

    public ResponseEntity<Void> execute() {
        var id = idSupplier.get();
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
