package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public class DeleteAction<TEntityId> {

    private CrudRepository<?, TEntityId> repository;

    private Supplier<TEntityId> idSupplier = IllegalActionParam.missingParam(DeleteAction.class, "by");

    public DeleteAction(CrudRepository<?, TEntityId> repository) {
        this.repository = repository;
    }

    public DeleteAction<TEntityId> by(TEntityId id) {
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
