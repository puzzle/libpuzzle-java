package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class FindAction<TEntity, TEntityId, TDto> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    private Class<TDto> dtoClass;

    public FindAction(CrudRepository<TEntity, TEntityId> repository, DtoMapper mapper, Class<TDto> dtoClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.dtoClass = dtoClass;
    }

    public ResponseEntity<TDto> execute(TEntityId id) {
        Optional<TEntity> entity = repository.findById(id);
        if (!entity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TDto dto = mapper.map(entity.get(), dtoClass);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
