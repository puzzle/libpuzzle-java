package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.CreateAction;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception.ActionAssertionError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MvcResult;

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create.CreateActionConfigurer.mockedCreateAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateActionResultMatchersTest {

    @Mock
    private RestActions<?, ?, CreateAction<Object>, ?, ?> restActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedCreateAction(restActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class WithParameter {

        @Test
        public void testNoInvocation() {
            restActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(new Object()).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(equalTo(new Object())).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            restActions.create().with("");
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(new Object()).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(equalTo(new Object())).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            var object = new Object();
            restActions.create().with(object);
            CreateActionMatchers.createAction().with(object).match(mvcResult);
            CreateActionMatchers.createAction().with(equalTo(object)).match(mvcResult);
        }
    }

    @Nested
    public class DtoParameter {

        @Test
        public void testNoInvocation() {
            restActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().from(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().from(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            restActions.create().from(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().from(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().from(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.create().from(Object.class);
            CreateActionMatchers.createAction().from(Object.class).match(mvcResult);
            CreateActionMatchers.createAction().from(equalTo(Object.class)).match(mvcResult);
        }
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            restActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed().match(mvcResult)
            );
        }


        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.create().execute(Object.class);
            CreateActionMatchers.createAction().executed().match(mvcResult);
        }
    }

    @Nested
    public class ExecutedWithResponseType {

        @Test
        public void testNoInvocation() {
            restActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            restActions.create().execute(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.create().execute(Object.class);
            CreateActionMatchers.createAction().executed(Object.class).match(mvcResult);
            CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult);
        }
    }
}
