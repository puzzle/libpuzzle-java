package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update;


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

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.update.UpdateActionConfigurer.mockedUpdateAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateActionResultMatchersTest {

    @Mock
    private CrudActions<?, Object, ?, ?> crudActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedUpdateAction(crudActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class IdParameter {

        @Test
        public void testNoInvocation() {
            crudActions.update();
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().by(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().by(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.update().by(1);
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().by(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().by(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.update().by(1);
            UpdateActionMatchers.updateAction().by(1).match(mvcResult);
            UpdateActionMatchers.updateAction().by(equalTo(1)).match(mvcResult);
        }
    }

    @Nested
    public class DtoParameter {

        @Test
        public void testNoInvocation() {
            crudActions.update();
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().with(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().with(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.update().with(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().with(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().with(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.update().with(Object.class);
            UpdateActionMatchers.updateAction().with(Object.class).match(mvcResult);
            UpdateActionMatchers.updateAction().with(equalTo(Object.class)).match(mvcResult);
        }
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            crudActions.update();
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().executed().match(mvcResult)
            );
        }


        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.update().execute(Object.class);
            UpdateActionMatchers.updateAction().executed().match(mvcResult);
        }
    }

    @Nested
    public class ExecutedWithResponseType {

        @Test
        public void testNoInvocation() {
            crudActions.update();
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            crudActions.update().execute(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.update().execute(Object.class);
            UpdateActionMatchers.updateAction().executed(Object.class).match(mvcResult);
            UpdateActionMatchers.updateAction().executed(equalTo(Object.class)).match(mvcResult);
        }
    }

    @Nested
    public class NotExecuted {

        @Test
        public void testWithInvocation() {
            crudActions.update().execute(Object.class);
            assertThrows(ActionAssertionError.class, () ->
                    UpdateActionMatchers.updateAction().notExecuted().match(mvcResult)
            );
        }

        @Test
        public void testWithoutInvocation() throws Exception {
            crudActions.update();
            UpdateActionMatchers.updateAction().notExecuted().match(mvcResult);
        }
    }
}
