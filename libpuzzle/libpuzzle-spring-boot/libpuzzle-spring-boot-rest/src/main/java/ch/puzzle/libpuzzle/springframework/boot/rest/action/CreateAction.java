package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateAction<TEntity, TResponseDto> {

    private CrudRepository<TEntity, ?> repository;

    private DtoMapper mapper;
    private Class<TResponseDto> responseDtoClass;

    public CreateAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper, Class<TResponseDto> responseDtoClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.responseDtoClass = responseDtoClass;
    }

    public <TRequestDto> ResponseEntity<TResponseDto> execute(
            TRequestDto dto,
            TEntity entity
    ) {
        mapper.map(dto, entity);
        repository.save(entity);
        TResponseDto responseDto = mapper.map(entity, responseDtoClass);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

}
