package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UpdateAction<TEntity, TEntityId, TResponseDto> {

    private CrudRepository<TEntity, TEntityId> repository;

    private DtoMapper mapper;
    private Class<TResponseDto> responseDtoClass;

    public UpdateAction(
            CrudRepository<TEntity, TEntityId> repository,
            DtoMapper mapper,
            Class<TResponseDto> responseDtoClass
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.responseDtoClass = responseDtoClass;
    }

    public <TDto> ResponseEntity<TResponseDto> execute(TDto dto, TEntityId id) {
        return repository.findById(id)
                .map(entity -> mapAndSave(dto, entity))
                .map(entity -> mapper.map(entity, responseDtoClass))
                .map(responseDto -> new ResponseEntity<>(responseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private <TDto> TEntity mapAndSave(TDto dto, TEntity entity) {
        mapper.map(dto, entity);
        return repository.save(entity);
    }

}
