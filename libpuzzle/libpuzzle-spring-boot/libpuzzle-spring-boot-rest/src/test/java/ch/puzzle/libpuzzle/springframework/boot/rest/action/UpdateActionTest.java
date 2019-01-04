package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateActionTest {

    private static final String EXISTING_ENTITY_ID = "the-id";

    private static final String NOT_EXISTING_ENTITY_ID = "another-id";

    private UpdateAction<Object, String, Object> action;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object entity;

    @Mock
    private Object requestDto;

    @Mock
    private Object responseDto;

    @Before
    public void setup() {
        action = new UpdateAction<>(repository, dtoMapper, Object.class);
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(repository.findById(eq(EXISTING_ENTITY_ID))).thenReturn(Optional.of(entity));
        when(dtoMapper.map(same(entity), eq(Object.class))).thenReturn(responseDto);
        when(repository.save(any())).thenReturn(entity);
    }

    @Test
    public void testExistingEntity() {
        var response = action.execute(requestDto, EXISTING_ENTITY_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(responseDto, response.getBody());
        verify(repository).findById(any());
        verify(repository).findById(EXISTING_ENTITY_ID);
        verify(repository).save(same(entity));
        verify(dtoMapper).map(same(requestDto), same(entity));
    }

    @Test
    public void testEntityNotFound() {
        var response = action.execute(requestDto, NOT_EXISTING_ENTITY_ID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).findById(any());
        verify(repository).findById(NOT_EXISTING_ENTITY_ID);
        verify(repository, never()).save(same(entity));
        verify(dtoMapper, never()).map(any(), any());
    }
}
