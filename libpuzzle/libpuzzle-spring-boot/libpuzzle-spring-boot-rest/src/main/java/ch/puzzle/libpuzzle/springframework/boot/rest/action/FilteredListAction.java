package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.dto.PageDto;
import ch.puzzle.libpuzzle.springframework.boot.rest.filter.FilterSpecificationFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilteredListAction<TEntity, TDto, TFilter> {

    private JpaSpecificationExecutor<TEntity> repository;

    private DtoMapper mapper;

    private Class<TDto> dtoClass;

    private FilterSpecificationFactory<TFilter> filterSpecificationFactory;

    public FilteredListAction(
            JpaSpecificationExecutor<TEntity> repository,
            DtoMapper mapper,
            Class<TDto> dtoClass,
            FilterSpecificationFactory<TFilter> filterSpecificationFactory
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.dtoClass = dtoClass;
        this.filterSpecificationFactory = filterSpecificationFactory;
    }

    public ResponseEntity<PageDto<TDto>> execute(TFilter filter) {
        return execute(filter, Pageable.unpaged());
    }

    public ResponseEntity<PageDto<TDto>> execute(TFilter filter, int pageSize, int page, String orderBy) {
        return execute(filter, createPageable(page, pageSize, orderBy));
    }

    public ResponseEntity<PageDto<TDto>> execute(TFilter filter, Pageable pageable) {
        Specification<TEntity> specification = filterSpecificationFactory.create(filter);
        PageDto<TDto> pageDto = new PageDto<>(
                pageable.isUnpaged() ? -1 : pageable.getPageNumber(),
                pageable.isUnpaged() ? -1 : pageable.getPageSize(),
                repository.findAll(specification, pageable).stream()
                        .map(entity -> mapper.map(entity, dtoClass))
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    private static Pageable createPageable(final int page, final int pageSize, final String sortString) {
        return PageRequest.of(page, pageSize, extractSort(sortString));
    }

    private static Sort extractSort(final String sortString) {
        final List<String> sortings = Arrays.asList(sortString.replace("%2C", ",").split(","));
        final List<Sort.Order> orderings = sortings.stream()
                .map(str -> str.replace("%20", " ").split(" "))
                .filter(array -> array.length == 2)
                .map(sort -> new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]))
                .collect(Collectors.toList());
        return Sort.by(orderings);

    }
}
