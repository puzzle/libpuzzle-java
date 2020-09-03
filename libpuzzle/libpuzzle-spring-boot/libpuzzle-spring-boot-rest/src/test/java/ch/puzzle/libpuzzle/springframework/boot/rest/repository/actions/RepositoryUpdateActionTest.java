package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.update.UpdateActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryUpdateActionTest {

    private static final String EXISTING_ENTITY_ID = "the-id";

    private static final String NOT_EXISTING_ENTITY_ID = "another-id";

    private RepositoryUpdateAction<Object, String> action;

    @Mock
    private UpdateActionParameters<String, Object> params;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object entity;

    @Mock
    private Object requestDto;

    @Before
    public void setup() {
        action = new RepositoryUpdateAction<>(repository, dtoMapper);
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(repository.findById(eq(EXISTING_ENTITY_ID))).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        doReturn(ActionParameter.holding(EXISTING_ENTITY_ID)).when(params).identifier();
        doReturn(ActionParameter.holding(requestDto)).when(params).requestDto();
    }

    @Test
    public void testExistingEntity() {
        var response = action.execute(params);
        assertSame(entity, response);
        verify(repository).findById(any());
        verify(repository).findById(EXISTING_ENTITY_ID);
        verify(repository).save(same(entity));
        verify(dtoMapper).map(same(requestDto), same(entity));
    }

    @Test(expected = RuntimeException.class) // FIXME
    public void testEntityNotFound() {
        doReturn(ActionParameter.holding(NOT_EXISTING_ENTITY_ID)).when(params).identifier();
        var response = action.execute(params);
        verify(repository).findById(any());
        verify(repository).findById(NOT_EXISTING_ENTITY_ID);
        verify(repository, never()).save(same(entity));
        verify(dtoMapper, never()).map(any(), any());
    }
}
