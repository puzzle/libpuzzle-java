package ch.puzzle.libpuzzle.springframework.boot.rest.action.find;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapper.DtoMapper;
import ch.puzzle.libpuzzle.springframework.boot.rest.mapping.ActionContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FindActionExecutionTest {

    private FindActionExecution<Object, Object> execution;

    @Mock
    private FindAction<Object, Object> action;

    @Mock
    private ActionContext actionContext;

    @Mock
    private DtoMapper dtoMapper;

    @Before
    public void setup() {
        execution = new FindActionExecution<>(action, actionContext);
        doReturn(dtoMapper).when(actionContext).getMapper();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testIdentifierIsRequired() {
        execution.identifier().get();
    }

    @Test
    public void testParamsAreSetCorrectly() {
        var identifier = new Object();
        var responseDtoClass = Object.class;
        execution.by(identifier).execute(responseDtoClass);
        verify(action).execute(argThat(params ->
                params.identifier().get() == identifier
        ));
    }
}
