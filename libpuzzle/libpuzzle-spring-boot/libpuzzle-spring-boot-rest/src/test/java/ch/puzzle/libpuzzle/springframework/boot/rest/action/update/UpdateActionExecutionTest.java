package ch.puzzle.libpuzzle.springframework.boot.rest.action.update;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.create.CreateActionExecution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateActionExecutionTest {

    private UpdateActionExecution<Object, Object, Object, Object> execution;

    @Mock
    private UpdateAction<Object> action;

    @Before
    public void setup() {
        execution = new UpdateActionExecution<>(action);
    }

    @Test(expected = ParameterNotSetException.class)
    public void testRequestDtoIsRequired() {
        execution.requestDto().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testIdentifierIsRequired() {
        execution.identifier().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testResponseDtoClassIsRequired() {
        execution.responseDtoClass().get();
    }

    @Test
    public void testParamsAreSetCorrectly() {
        var requestDto = new Object();
        var identifier = new Object();
        var responseDtoClass = Object.class;
        execution.with(requestDto).by(identifier).execute(responseDtoClass);
        verify(action).execute(argThat(params ->
                params.identifier().get() == identifier &&
                        params.requestDto().get() == requestDto &&
                        params.responseDtoClass().get() == responseDtoClass
        ));
    }
}
