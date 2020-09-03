package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateActionExecutionTest {

    private CreateActionExecution<Object, Object> execution;

    @Mock
    private CreateAction<Object> action;

    @Mock
    private ActionContext actionContext;

    @Mock
    private DtoMapper dtoMapper;

    @Before
    public void setup() {
        execution = new CreateActionExecution<>(action, actionContext);
        doReturn(dtoMapper).when(actionContext).getMapper();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testRequestDtoIsRequired() {
        execution.requestDto().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testEntityIsRequired() {
        execution.entity().get();
    }

    @Test
    public void testParamsAreSetCorrectly() {
        var requestDto = new Object();
        var entity = new Object();
        var responseDtoClass = Object.class;
        execution.with(requestDto).using(entity).execute(responseDtoClass);
        verify(action).execute(argThat(params ->
                params.entity().get() == entity &&
                        params.requestDto().get() == requestDto
        ));
    }
}
