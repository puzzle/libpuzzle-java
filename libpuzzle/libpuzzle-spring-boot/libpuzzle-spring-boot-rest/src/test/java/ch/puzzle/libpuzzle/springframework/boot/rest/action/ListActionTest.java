package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListActionTest {

    private ListAction<Object> action;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object entityOne;

    @Mock
    private Object entityTwo;

    @Mock
    private Object responseDtoOne;

    @Mock
    private Object responseDtoTwo;

    @Before
    public void setup() {
        action = new ListAction<>(repository, dtoMapper);
        when(repository.findAll()).thenReturn(List.of(entityOne, entityTwo));
        when(dtoMapper.map(same(entityOne), eq(Object.class))).thenReturn(responseDtoOne);
        when(dtoMapper.map(same(entityTwo), eq(Object.class))).thenReturn(responseDtoTwo);
    }

    @Test
    public void testListAll() {
        var response = action.execute(Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertSame(responseDtoOne, response.getBody().get(0));
        assertSame(responseDtoTwo, response.getBody().get(1));
        verify(repository).findAll();
    }
}
