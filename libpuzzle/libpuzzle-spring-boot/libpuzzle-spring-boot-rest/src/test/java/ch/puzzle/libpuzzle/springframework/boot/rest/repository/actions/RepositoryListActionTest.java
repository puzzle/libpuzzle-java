package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionBuilder;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.list.ListActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryListActionTest {

    private RepositoryListAction<Object> action;

    @Mock
    private ListActionParameters<Predicate<Object>> params;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object entityOne;

    @Mock
    private Object entityTwo;

    @Before
    public void setup() {
        action = new RepositoryListAction<>(repository, dtoMapper);
        when(repository.findAll()).thenReturn(List.of(entityOne, entityTwo));
        doReturn(ActionParameter.holding(0)).when(params).offset();
        doReturn(ActionParameter.holding(100)).when(params).limit();
        doReturn(ActionParameter.empty(ListActionBuilder.class, "matching")).when(params).filter();
    }

    @Test
    public void testListAll() {
        var response = action.execute(params);
        var entries = StreamSupport.stream(response.spliterator(), false).collect(Collectors.toList());
        Assert.assertEquals(2, entries.size());
        assertSame(entityOne, entries.get(0));
        assertSame(entityTwo, entries.get(1));
        verify(repository).findAll();
    }
}
