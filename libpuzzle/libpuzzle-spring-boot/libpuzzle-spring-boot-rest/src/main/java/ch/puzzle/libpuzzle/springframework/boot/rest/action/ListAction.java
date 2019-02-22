package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ListAction<TEntity> {

    protected CrudRepository<TEntity, ?> repository;

    protected DtoMapper mapper;

    public ListAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public <TResponseDto> ResponseEntity<List<TResponseDto>> execute(Class<TResponseDto> responseDtoClass) {
        List<TResponseDto> list = StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(entity -> mapper.map(entity, responseDtoClass))
                .collect(Collectors.toList());
        // FIXME: Make execute code configurable
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
