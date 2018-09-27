package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateAction<TEntity, TDto> {

    private CrudRepository<TEntity, ?> repository;

    private DtoMapper mapper;

    public CreateAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseEntity<TDto> execute(TDto dto, TEntity entity) {
        mapper.map(dto, entity);
        repository.save(entity);
        mapper.map(entity, dto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}
