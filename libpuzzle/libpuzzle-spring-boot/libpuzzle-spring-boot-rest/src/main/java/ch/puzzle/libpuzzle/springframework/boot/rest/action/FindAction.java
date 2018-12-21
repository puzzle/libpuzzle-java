package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class FindAction<TEntity, TEntityId, TResponseDto> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;

    private Class<TResponseDto> responseDtoClass;

    public FindAction(
            CrudRepository<TEntity, TEntityId> repository,
            DtoMapper mapper,
            Class<TResponseDto> responseDtoClass
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.responseDtoClass = responseDtoClass;
    }

    public ResponseEntity<TResponseDto> execute(TEntityId id) {
        Optional<TEntity> entity = repository.findById(id);
        if (!entity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TResponseDto dto = mapper.map(entity.get(), responseDtoClass);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
