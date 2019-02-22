package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Supplier;

public class FindAction<TEntity, TEntityId> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    private Supplier<TEntityId> idSupplier = IllegalActionParam.missingParam(FindAction.class, "by");

    public FindAction(
            CrudRepository<TEntity, TEntityId> repository,
            DtoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public FindAction<TEntity, TEntityId> by(TEntityId id) {
        idSupplier = () -> id;
        return this;
    }

    public <TResponseDto> ResponseEntity<TResponseDto> execute(Class<TResponseDto> responseDtoClass) {
        Optional<TEntity> entity = repository.findById(idSupplier.get());
        if (entity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TResponseDto dto = mapper.map(entity.get(), responseDtoClass);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
