package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.dto.PageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PageListAction<TEntity, TResponseDto> extends ListAction<TEntity, TResponseDto> {

    private JpaRepository<TEntity, ?> repository;

    public PageListAction(
            JpaRepository<TEntity, ?> repository,
            DtoMapper mapper,
            Class<TResponseDto> responseDtoClass
    ) {
        super(repository, mapper, responseDtoClass);
        this.repository = repository;
    }

    /*public ResponseEntity<PageDto<TDto>> execute(String rql) {
        final RsqlParameters parameters = RsqlParameters.from(rql);
        final Pageable pageable = parameters.getPageRequest().orElseGet(Pageable::unpaged);
        return execute(pageable);
    }*/

    public ResponseEntity<PageDto<TResponseDto>> execute(Pageable pageable) {
        List<TResponseDto> list = StreamSupport.stream(repository.findAll(pageable).spliterator(), true)
                .map(entity -> mapper.map(entity, responseDtoClass))
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new PageDto<>(pageable.getPageNumber(), pageable.getPageSize(), list),
                HttpStatus.OK
        );
    }

}
