package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.create;


import ch.puzzle.libpuzzle.springframework.boot.rest.action.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception.ActionAssertionError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateActionResultMatchersTest {

    @Mock
    private CrudActions crudActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedCreateAction(crudActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class WithParameter {

        @Test
        public void testNoInvocation() {
            crudActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().using(new Object()).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().using(equalTo(new Object())).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.create().using("");
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().using(new Object()).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().using(equalTo(new Object())).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            var object = new Object();
            crudActions.create().using(object);
            CreateActionMatchers.createAction().using(object).match(mvcResult);
            CreateActionMatchers.createAction().using(equalTo(object)).match(mvcResult);
        }
    }

    @Nested
    public class DtoParameter {

        @Test
        public void testNoInvocation() {
            crudActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.create().with(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().with(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.create().with(Object.class);
            CreateActionMatchers.createAction().with(Object.class).match(mvcResult);
            CreateActionMatchers.createAction().with(equalTo(Object.class)).match(mvcResult);
        }
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            crudActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed().match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.create().execute(Object.class);
            CreateActionMatchers.createAction().executed().match(mvcResult);
        }

    }

    @Nested
    public class ExecutedWithResponseType {

        @Test
        public void testNoInvocation() {
            crudActions.create();
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            crudActions.create().execute(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.create().execute(Object.class);
            CreateActionMatchers.createAction().executed(Object.class).match(mvcResult);
            CreateActionMatchers.createAction().executed(equalTo(Object.class)).match(mvcResult);
        }

    }

    @Nested
    public class NotExecuted {

        @Test
        public void testWithInvocation() {
            crudActions.create().execute(Object.class);
            assertThrows(ActionAssertionError.class, () ->
                    CreateActionMatchers.createAction().notExecuted().match(mvcResult)
            );
        }

        @Test
        public void testWithoutInvocation() throws Exception {
            crudActions.create();
            CreateActionMatchers.createAction().notExecuted().match(mvcResult);
        }
    }
}
