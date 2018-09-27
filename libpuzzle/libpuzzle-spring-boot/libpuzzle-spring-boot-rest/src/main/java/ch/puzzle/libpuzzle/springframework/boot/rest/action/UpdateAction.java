package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UpdateAction<TEntity, TEntityId, TDto> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    public UpdateAction(CrudRepository<TEntity, TEntityId> repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ResponseEntity<TDto> execute(TDto dto, TEntityId id) {
        Optional<TEntity> optionalEntity = repository.findById(id);
        if (!optionalEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TEntity entity = optionalEntity.get();
        mapper.map(dto, entity);
        repository.save(entity);
        mapper.map(entity, dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
