package ch.puzzle.libpuzzle.springframework.boot.rest.action.list;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListActionExecutionTest {

    private static final int DEFAULT_OFFSET = 100;

    private static final int DEFAULT_LIMIT = 200;

    private ListActionExecution<Object, Object> execution;

    @Mock
    private ListAction<Object> action;

    @Before
    public void setup() {
        execution = new ListActionExecution<>(action, DEFAULT_OFFSET, DEFAULT_LIMIT);
    }

    @Test
    public void testOffsetHasDefaultValue() {
        int offset = execution.params.offset().get();
        assertEquals(DEFAULT_OFFSET, offset);
    }

    @Test
    public void testLimitHasDefaultValue() {
        int limit = execution.params.limit().get();
        assertEquals(DEFAULT_LIMIT, limit);
    }

    @Test(expected = ParameterNotSetException.class)
    public void testFilterIsRequired() {
        execution.params.filter().get();
    }

    @Test(expected = ParameterNotSetException.class)
    public void testResponseDtoClassIsRequired() {
        execution.params.responseDtoClass().get();
    }

    @Test
    public void testParamsAreSetCorrectly() {
        var offset = 10;
        var limit = 20;
        var filter = new Object();
        var responseDtoClass = Object.class;
        execution.matching(filter).skip(offset).limit(limit).execute(responseDtoClass);
        verify(action).execute(argThat(params ->
                params.filter().get() == filter &&
                        params.offset().get() == offset &&
                        params.limit().get() == limit &&
                        params.responseDtoClass().get() == responseDtoClass
        ));
    }
}
