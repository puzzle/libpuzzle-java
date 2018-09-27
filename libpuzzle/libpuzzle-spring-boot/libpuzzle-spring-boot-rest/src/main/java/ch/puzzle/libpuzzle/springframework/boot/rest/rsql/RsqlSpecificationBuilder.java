package ch.puzzle.libpuzzle.springframework.boot.rest.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class RsqlSpecificationBuilder<TEntity> {

    private Specification<TEntity> createSpecification(final Node node) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node);
        }
        throw new RuntimeException("Unsupported node type."); // @FIXME
    }

    Specification<TEntity> createSpecification(final LogicalNode logicalNode) {
        final List<Specification<TEntity>> specs = logicalNode.getChildren()
                .stream()
                .map(this::createSpecification)
                .collect(Collectors.toList());
        final Specification<TEntity> result;
        switch (logicalNode.getOperator()) {
            case AND:
                result = specs.stream().reduce((spec1, spec2) -> Specification.where(spec1).and(spec2)).get(); // @FIXME
                break;
            case OR:
                result = specs.stream().reduce((spec1, spec2) -> Specification.where(spec1).or(spec2)).get(); // @FIXME
                break;
            default:
                throw new RuntimeException("Unsupported logical node."); // @FIXME
        }
        return result;
    }

    Specification<TEntity> createSpecification(final ComparisonNode comparisonNode) {
        return Specification.where(
                new RsqlSpecification<>(
                        comparisonNode.getSelector(),
                        comparisonNode.getOperator(),
                        comparisonNode.getArguments()
                )
        );
    }
}
