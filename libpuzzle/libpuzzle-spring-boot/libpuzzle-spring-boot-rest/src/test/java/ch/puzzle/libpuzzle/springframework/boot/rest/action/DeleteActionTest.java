package ch.puzzle.libpuzzle.springframework.boot.rest.action;

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
public class DeleteActionTest {

    private static final String EXISTING_ENTITY_ID = "the-id";

    private static final String NOT_EXISTING_ENTITY_ID = "another-id";

    private DeleteAction<Object, String> action;

    @Mock
    private CrudRepository<Object, String> repository;

    @Before
    public void setup() {
        action = new DeleteAction<>(repository);
        when(repository.existsById(EXISTING_ENTITY_ID)).thenReturn(true);
        when(repository.existsById(NOT_EXISTING_ENTITY_ID)).thenReturn(false);
    }

    @Test
    public void testExistingEntity() {
        var response = action.execute(EXISTING_ENTITY_ID);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository).deleteById(EXISTING_ENTITY_ID);
    }

    @Test
    public void testEntityNotFound() {
        var response = action.execute(NOT_EXISTING_ENTITY_ID);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(repository, never()).deleteById(any());
    }
}
