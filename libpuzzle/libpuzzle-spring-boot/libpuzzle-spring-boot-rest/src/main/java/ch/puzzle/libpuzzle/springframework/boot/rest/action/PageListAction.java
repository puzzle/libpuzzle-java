package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.dto.PageDto;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PageListAction<TEntity, TDto> extends ListAction<TEntity, TDto> {

    private JpaRepository<TEntity, ?> repository;

    public PageListAction(JpaRepository<TEntity, ?> repository, DtoMapper mapper, Class<TDto> dtoClass) {
        super(repository, mapper, dtoClass);
        this.repository = repository;
    }

    /*public ResponseEntity<PageDto<TDto>> execute(String rql) {
        final RsqlParameters parameters = RsqlParameters.from(rql);
        final Pageable pageable = parameters.getPageRequest().orElseGet(Pageable::unpaged);
        return execute(pageable);
    }*/

    public ResponseEntity<PageDto<TDto>> execute(Pageable pageable) {
        List<TDto> list = StreamSupport.stream(repository.findAll(pageable).spliterator(), true)
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new PageDto<>(pageable.getPageNumber(), pageable.getPageSize(), list),
                HttpStatus.OK
        );
    }

}
