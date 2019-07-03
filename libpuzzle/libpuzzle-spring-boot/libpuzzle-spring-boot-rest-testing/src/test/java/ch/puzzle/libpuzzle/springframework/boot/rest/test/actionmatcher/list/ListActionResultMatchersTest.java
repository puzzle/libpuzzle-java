package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;


import ch.puzzle.libpuzzle.springframework.boot.rest.CrudActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.ListAction;
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

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list.ListActionConfigurer.mockedListAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ListActionResultMatchersTest {

    @Mock
    private CrudActions<ListAction<Object>, ?, ?, ?, ?> crudActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedListAction(crudActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            crudActions.list();
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().executed().match(mvcResult)
            );
        }


        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.list().execute(Object.class);
            ListActionMatchers.listAction().executed().match(mvcResult);
        }
    }

    @Nested
    public class ExecutedWithResponseType {

        @Test
        public void testNoInvocation() {
            crudActions.list();
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            crudActions.list().execute(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.list().execute(Object.class);
            ListActionMatchers.listAction().executed(Object.class).match(mvcResult);
            ListActionMatchers.listAction().executed(equalTo(Object.class)).match(mvcResult);
        }
    }
}
