package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.delete.DeleteActionParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryDeleteActionTest {

    private static final String EXISTING_ENTITY_ID = "the-id";

    private static final String NOT_EXISTING_ENTITY_ID = "another-id";

    private RepositoryDeleteAction<String> action;

    @Mock
    private DeleteActionParameters<String> params;

    @Mock
    private CrudRepository<Object, String> repository;

    @Before
    public void setup() {
        action = new RepositoryDeleteAction<>(repository);
        when(repository.existsById(EXISTING_ENTITY_ID)).thenReturn(true);
        when(repository.existsById(NOT_EXISTING_ENTITY_ID)).thenReturn(false);
        doReturn(ActionParameter.holding(EXISTING_ENTITY_ID)).when(params).identifier();
    }

    @Test
    public void testExistingEntity() {
        action.execute(params);
        verify(repository).deleteById(EXISTING_ENTITY_ID);
    }

    @Test(expected = RuntimeException.class) // FIXME
    public void testEntityNotFound() {
        doReturn(ActionParameter.holding(NOT_EXISTING_ENTITY_ID)).when(params).identifier();
        var response = action.execute(params);
        verify(repository, never()).deleteById(any());
    }
}
