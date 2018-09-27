package ch.puzzle.libpuzzle.springframework.boot.rest.rsql;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

public class SpecifcationRsqlVisitor<TEntity> implements RSQLVisitor<Specification<TEntity>, Void> {

    private RsqlSpecificationBuilder<TEntity> specificationBuilder;

    public SpecifcationRsqlVisitor(RsqlSpecificationBuilder<TEntity> specificationBuilder) {
        this.specificationBuilder = specificationBuilder;
    }

    @Override
    public Specification<TEntity> visit(AndNode andNode, Void aVoid) {
        return specificationBuilder.createSpecification(andNode);
    }

    @Override
    public Specification<TEntity> visit(OrNode orNode, Void aVoid) {
        return specificationBuilder.createSpecification(orNode);
    }

    @Override
    public Specification<TEntity> visit(ComparisonNode comparisonNode, Void aVoid) {
        return specificationBuilder.createSpecification(comparisonNode);
    }
}
