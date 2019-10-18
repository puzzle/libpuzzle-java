package ch.puzzle.libpuzzle.springframework.boot.rest.action.create;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateActionExecutionTest {

    private CreateActionExecution<Object, Object, Object> execution;

    @Mock
    private CreateAction<Object> action;

    @Before
    public void setup() {
        execution = new CreateActionExecution<>(action);
    }

    @Test(expected = ParameterNotSetException.class)
    public void testRequestDtoIsRequired() {
        execution.requestDto().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testEntityIsRequired() {
        execution.entity().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testResponseDtoClassIsRequired() {
        execution.responseDtoClass().get();
    }

    @Test
    public void testParamsAreSetCorrectly() {
        var requestDto = new Object();
        var entity = new Object();
        var responseDtoClass = Object.class;
        execution.with(requestDto).using(entity).execute(responseDtoClass);
        verify(action).execute(argThat(params ->
                params.entity().get() == entity &&
                        params.requestDto().get() == requestDto &&
                        params.responseDtoClass().get() == responseDtoClass
        ));
    }
}
