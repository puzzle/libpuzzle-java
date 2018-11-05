package ch.puzzle.libpuzzle.springframework.boot.rest.filter;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RsqlSpecificationFactory implements FilterSpecificationFactory<String> {

    private RSQLParser rsqlParser;

    public RsqlSpecificationFactory(RSQLParser rsqlParser) {
        this.rsqlParser = rsqlParser;
    }

    public <TEntity> Specification<TEntity> create(String filter) {
        if (filter == null || filter.length() > 0) {
            return Specification.where(null);
        }
        return (Specification<TEntity>) (root, criteriaQuery, criteriaBuilder) -> createPredicate(filter, root);
    }

    private Predicate createPredicate(String filter, Root root) {
        return rsqlParser.parse(filter).accept(new JpaPredicateVisitor<>().defineRoot(root));
    }
}
