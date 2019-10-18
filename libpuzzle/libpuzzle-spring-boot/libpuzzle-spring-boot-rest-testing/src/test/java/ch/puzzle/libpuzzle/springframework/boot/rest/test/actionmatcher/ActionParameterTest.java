package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher;

import ch.puzzle.libpuzzle.springframework.boot.rest.action.ActionParameter;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ParameterNotSetException;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ActionParameterTest {

    @Test
    public void testGet() {
        var value = new Object();
        var param = ActionParameter.holding(value);
        assertSame(value, param.get());
    }

    @Test(expected = ParameterNotSetException.class)
    public void testGetWithEmptyParam() {
        var param = ActionParameter.empty(Object.class, "equals");
        param.get();
    }

    @Test
    public void testOrDefault() {
        var value = new Object();
        var param = ActionParameter.holding(value);
        assertSame(value, param.orDefault(new Object()));
    }

    @Test
    public void testOrDefaultWithEmptyParam() {
        var defaultValue = new Object();
        var param = ActionParameter.empty(Object.class, "equals");
        assertSame(defaultValue, param.orDefault(defaultValue));
    }
}
