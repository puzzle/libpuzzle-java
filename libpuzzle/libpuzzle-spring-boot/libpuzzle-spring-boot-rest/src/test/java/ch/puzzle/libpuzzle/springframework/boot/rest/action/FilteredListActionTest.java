package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.filter.FilterSpecificationFactory;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FilteredListActionTest {

    private FilteredListAction<Object, Object, String> action;


    @Mock
    private JpaSpecificationExecutor<Object> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private FilterSpecificationFactory<String> filterSpecificationFactory;

    @Mock
    private Specification<Object> filterSpecification;

    @Before
    public void setup() {
        action = new FilteredListAction<>(repository, dtoMapper, Object.class, filterSpecificationFactory);
        when(repository.findAll(any(), any(Pageable.class))).thenReturn(Page.empty());
        when(filterSpecificationFactory.create(anyString())).thenReturn(filterSpecification);
    }

    @Test
    public void testWithQuery() {
        String query = "the-query";
        action.execute(query);
        action.execute(query, mock(Pageable.class));
        action.execute(query, 1, 1, "column asc, column desc");

        verify(repository, times(3)).findAll(same(filterSpecification), any(Pageable.class));
    }

    @Test
    public void testWithoutQuery() {
        action.execute("");
        action.execute("", mock(Pageable.class));
        action.execute("", 1, 1, "column asc, column desc");

        verify(repository, times(3)).findAll(same(filterSpecification), any(Pageable.class));
    }


}
