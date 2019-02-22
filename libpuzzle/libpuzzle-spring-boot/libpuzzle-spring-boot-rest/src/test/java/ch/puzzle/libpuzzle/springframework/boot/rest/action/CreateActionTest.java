package ch.puzzle.libpuzzle.springframework.boot.rest.action;

import ch.puzzle.libpuzzle.springframework.boot.rest.IllegalActionParam;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateActionTest {

    private CreateAction<Object> action;

    @Mock
    private CrudRepository<Object, String> repository;

    @Mock
    private DtoMapper dtoMapper;

    @Mock
    private Object initialEntity;

    @Mock
    private Object persistedEntity;

    @Mock
    private Object requestDto;

    @Mock
    private Object responseDto;

    @Before
    public void setup() {
        action = new CreateAction<>(repository, dtoMapper);
        when(dtoMapper.map(same(persistedEntity), eq(Object.class))).thenReturn(responseDto);
        when(repository.save(any())).thenReturn(persistedEntity);
    }

    @Test
    public void testCreateEntity() {
        var response = action.from(requestDto).with(initialEntity).execute(Object.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(responseDto, response.getBody());
        verify(repository).save(same(initialEntity));
        verify(dtoMapper).map(same(requestDto), same(initialEntity));
        verify(dtoMapper).map(same(persistedEntity), eq(Object.class));
    }

    @Test(expected = IllegalActionParam.class)
    public void testMissingDto() {
        action.with(initialEntity).execute(Object.class);
    }

    @Test(expected = IllegalActionParam.class)
    public void testMissingInitialEntity() {
        action.from(requestDto).execute(Object.class);
    }

}
