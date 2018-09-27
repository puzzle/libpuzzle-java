package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.dto.PageDto;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.rsql.RsqlSpecification;
import com.mathianasj.spring.rsql.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FilteredListAction<TEntity, TDto> {

    private JpaSpecificationExecutor<TEntity> repository;

    private DtoMapper mapper;

    private Class<TDto> dtoClass;

    private RSQLParser rsqlParser;

    private RSQLVisitor<Specification<TEntity>, Void> rsqlVisitor;

    public FilteredListAction(
            JpaSpecificationExecutor<TEntity> repository,
            DtoMapper mapper,
            Class<TDto> dtoClass,
            RSQLParser rsqlParser,
            RSQLVisitor<Specification<TEntity>, Void> rsqlVisitor
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.dtoClass = dtoClass;
        this.rsqlParser = rsqlParser;
        this.rsqlVisitor = rsqlVisitor;
    }

    public FilteredListAction(JpaSpecificationExecutor<TEntity> repository, DtoMapper mapper, Class<TDto> dtoClass) {
        this(repository, mapper, dtoClass, new RSQLParser(), new CustomRsqlVisitor<>());
    }

    public ResponseEntity<PageDto<TDto>> execute(String filterRsql) {
        return execute(filterRsql, Pageable.unpaged());
    }

    public ResponseEntity<PageDto<TDto>> execute(String filterRsql, int pageSize, int page, String orderBy) {
        return execute(filterRsql, createPageable(page, pageSize, orderBy));
    }

    public ResponseEntity<PageDto<TDto>> execute(String filterRsql, Pageable pageable) {
        Specification<TEntity> specification = Specification.where(null);
        if (filterRsql != null && filterRsql.length() > 0) {
            specification = rsqlParser.parse(filterRsql).accept(rsqlVisitor);
        }
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
