package ch.puzzle.libpuzzle.springframework.boot.rest.filter;

import org.springframework.data.jpa.domain.Specification;

public interface FilterSpecificationFactory<TFilter> {

    <TEntity> Specification<TEntity> create(TFilter filter);

}
