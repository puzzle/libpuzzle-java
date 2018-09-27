package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.rsql.RsqlSpecification;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FilteredListActionTest {

    private FilteredListAction action;


    @Mock
    private JpaSpecificationExecutor<?> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private RSQLParser rsqlParser;

    @Mock
    private RSQLVisitor<Specification<?>, Void> rsqlVisitor;

    @Mock
    private RsqlSpecification rsqlSpecification;

    @Mock
    private AndNode rsqlNode;

    @Before
    public void setup() {
        action = new FilteredListAction(repository, dtoMapper, Object.class, rsqlParser, rsqlVisitor);
        when(repository.findAll(any(), any(Pageable.class))).thenReturn(Page.empty());
        when(rsqlParser.parse(anyString())).thenReturn(rsqlNode);
        when(rsqlNode.accept(any(RSQLVisitor.class))).thenReturn(rsqlSpecification);
    }

    @Test
    public void testWithQuery() {
        String query = "the-query";
        action.execute(query);
        action.execute(query, mock(Pageable.class));
        action.execute(query, 1, 1, "column asc, column desc");

        verify(rsqlParser, times(3)).parse(query);
        verify(repository, times(3)).findAll(eq(rsqlSpecification), any(Pageable.class));
    }

    @Test
    public void testWithoutQuery() {
        action.execute("");
        action.execute("", mock(Pageable.class));
        action.execute("", 1, 1, "column asc, column desc");

        verify(rsqlParser, never()).parse(anyString());
        verify(repository, times(3)).findAll(any(), any(Pageable.class));
    }


}
