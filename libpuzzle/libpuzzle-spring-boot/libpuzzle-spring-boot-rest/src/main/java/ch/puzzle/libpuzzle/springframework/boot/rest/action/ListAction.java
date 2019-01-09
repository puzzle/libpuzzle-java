package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ListAction<TEntity, TResponseDto> {

    protected CrudRepository<TEntity, ?> repository;

    protected DtoMapper mapper;

    protected Class<TResponseDto> responseDtoClass;

    public ListAction(CrudRepository<TEntity, ?> repository, DtoMapper mapper, Class<TResponseDto> responseDtoClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.responseDtoClass = responseDtoClass;
    }

    public ResponseEntity<List<TResponseDto>> execute() {
        List<TResponseDto> list = StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(entity -> mapper.map(entity, responseDtoClass))
                .collect(Collectors.toList());
        // FIXME: Make response code configurable
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
