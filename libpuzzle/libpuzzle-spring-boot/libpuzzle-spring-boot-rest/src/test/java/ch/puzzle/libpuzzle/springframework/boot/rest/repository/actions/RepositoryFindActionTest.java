package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.find.FindActionParameters;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryFindActionTest {

    private static final String EXISTING_ENTITY_ID = "the-id";

    private static final String NOT_EXISTING_ENTITY_ID = "another-id";

    private RepositoryFindAction<Object, String> action;

    @Mock
    private FindActionParameters<String> params;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object entity;

    @Before
    public void setup() {
        action = new RepositoryFindAction<>(repository, dtoMapper);
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(repository.findById(eq(EXISTING_ENTITY_ID))).thenReturn(Optional.of(entity));
        doReturn(ActionParameter.holding(EXISTING_ENTITY_ID)).when(params).identifier();
    }

    @Test
    public void testExistingEntity() {
        var response = action.execute(params);
        assertSame(response, entity);
        verify(repository).findById(any());
        verify(repository).findById(EXISTING_ENTITY_ID);
    }

    @Test(expected = RuntimeException.class)
    public void testEntityNotFound() {
        doReturn(ActionParameter.holding(NOT_EXISTING_ENTITY_ID)).when(params).identifier();
        var response = action.execute(params);
        assertNull(response);
        verify(repository).findById(any());
        verify(repository).findById(NOT_EXISTING_ENTITY_ID);
    }
}
