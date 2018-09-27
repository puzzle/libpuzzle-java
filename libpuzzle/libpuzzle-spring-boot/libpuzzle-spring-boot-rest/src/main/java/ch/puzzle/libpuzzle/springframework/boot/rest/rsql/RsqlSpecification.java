package ch.puzzle.libpuzzle.springframework.boot.rest.rsql;

import com.mathianasj.spring.rsql.RsqlSearchOperation;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class RsqlSpecification<TEntity> implements Specification<TEntity> {

    private final String selector;

    private final ComparisonOperator operator;

    private final List<String> arguments;

    RsqlSpecification(
            final String selector,
            final ComparisonOperator operator,
            final List<String> arguments
    ) {
        super();
        this.selector = selector;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public Predicate toPredicate(Root<TEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        final List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

            case EQUAL: {
                if (argument instanceof String) {
                    return criteriaBuilder.like(
                            root.get(selector), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return criteriaBuilder.isNull(root.get(selector));
                } else {
                    return criteriaBuilder.equal(root.get(selector), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return criteriaBuilder.notLike(
                            root.get(selector), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return criteriaBuilder.isNotNull(root.get(selector));
                } else {
                    return criteriaBuilder.notEqual(root.get(selector), argument);
                }
            }
            case GREATER_THAN: {
                return criteriaBuilder.greaterThan(root.get(selector), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                return criteriaBuilder.greaterThanOrEqualTo(
                        root.get(selector), argument.toString());
            }
            case LESS_THAN: {
                return criteriaBuilder.lessThan(root.get(selector), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                return criteriaBuilder.lessThanOrEqualTo(
                        root.get(selector), argument.toString());
            }
            case IN:
                return root.get(selector).in(args);
            case NOT_IN:
                return criteriaBuilder.not(root.get(selector).in(args));
        }

        return null;
    }

    private List<Object> castArguments(final Root<TEntity> root) {
        final List<Object> args = new LinkedList<>();
        final Class<?> type = root.get(selector).getJavaType();

        for (final String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else if (type.equals(LocalDate.class)) {
                args.add(LocalDateTime.parse(argument, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate());
            } else {
                args.add(argument);
            }
        }
        return args;
    }
}
