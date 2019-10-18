package ch.puzzle.libpuzzle.springframework.boot.rest.repository.actions;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionParameters;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryCreateActionTest {

    private RepositoryCreateAction<Object> action;

    @Mock
    private CreateActionParameters<Object, Object, Object> params;

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
        action = new RepositoryCreateAction<>(repository, dtoMapper);
        when(dtoMapper.map(same(persistedEntity), eq(Object.class))).thenReturn(responseDto);
        when(repository.save(any())).thenReturn(persistedEntity);
        doReturn(ActionParameter.holding(requestDto)).when(params).requestDto();
        doReturn(ActionParameter.holding(initialEntity)).when(params).entity();
        doReturn(ActionParameter.holding(Object.class)).when(params).responseDtoClass();
    }

    @Test
    public void testCreateEntity() {
        var response = action.execute(params);
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertSame(responseDto, response.getBody());
        verify(repository).save(same(initialEntity));
        verify(dtoMapper).map(same(requestDto), same(initialEntity));
        verify(dtoMapper).map(same(persistedEntity), eq(Object.class));
    }
}
