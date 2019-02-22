package ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.assertionerror.RestAssertionError;
import junit.framework.ComparisonFailure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.restassert.FindAssert.assertFind;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FindAssertTest {

    private static final String URL = "some-url";

    @Mock
    private RestActions<?, FindAction<Object, Object>, ?, ?, ?> restActions;

    @Mock
    private TestRestTemplate restTemplate;

    private FindAssert.Executor<Object, Object> assertionExecutor;

    @Before
    public void setup() {
        assertionExecutor = assertFind(restTemplate, restActions);
    }

    @Test(expected = RestAssertionError.class)
    public void testUsingWithoutInvocation() {
        assertionExecutor.using(URL);
    }

    @Test
    public void testUsingWithInvocation() {
        restActions.find();
        assertionExecutor.using(URL);
    }

    @Test(expected = RestAssertionError.class)
    public void testByWithoutInvocation() {
        restActions.find();
        assertionExecutor.using(URL).by(1);
    }

    @Test(expected = RestAssertionError.class)
    public void testByWithDifferentInvocation() {
        restActions.find().by(1);
        assertionExecutor.using(URL).by(2);
    }

    @Test
    public void testByWithCorrectInvocation() {
        restActions.find().by(1);
        assertionExecutor.using(URL).by(1);
    }

    @Test
    public void testWithResponseWithCorrectInvocation() {
        restActions.find().execute(Object.class);
        assertionExecutor.using(URL).withResponse(Object.class);
    }

    @Test(expected = RestAssertionError.class)
    public void testWithResponseWithoutInvocation() {
        restActions.find();
        assertionExecutor.using(URL).withResponse(Object.class);
    }

    @Test(expected = RestAssertionError.class)
    public void testWithResponseWithDifferentInvocation() {
        restActions.find().execute(String.class);
        assertionExecutor.using(URL).by(Object.class);
    }

}
