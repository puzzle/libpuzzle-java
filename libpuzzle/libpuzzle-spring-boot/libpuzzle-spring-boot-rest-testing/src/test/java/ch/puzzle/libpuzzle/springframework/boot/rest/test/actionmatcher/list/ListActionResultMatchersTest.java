package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list;


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

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.list.ListActionConfigurer.mockedListAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ListActionResultMatchersTest {

    @Mock
    private CrudActions<?, ?, Object> crudActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedListAction(crudActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class SkipParameter {

        @Test
        public void testNoInvocation() {
            crudActions.list();
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().skip(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().skip(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            crudActions.list().skip(1);
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().skip(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().skip(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.list().skip(1);
            ListActionMatchers.listAction().skip(1).match(mvcResult);
            ListActionMatchers.listAction().skip(equalTo(1)).match(mvcResult);
        }
    }

    @Nested
    public class LimitParameter {

        @Test
        public void testNoInvocation() {
            crudActions.list();
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().limit(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().limit(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.list().skip(1);
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().limit(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().limit(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.list().limit(1);
            ListActionMatchers.listAction().limit(1).match(mvcResult);
            ListActionMatchers.listAction().limit(equalTo(1)).match(mvcResult);
        }
    }

    @Nested
    public class MatchingParameter {

        @Test
        public void testNoInvocation() {
            crudActions.list();
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().matching(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().matching(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            crudActions.list().matching(1);
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().matching(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().matching(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            crudActions.list().matching(1);
            ListActionMatchers.listAction().matching(1).match(mvcResult);
            ListActionMatchers.listAction().matching(equalTo(1)).match(mvcResult);
        }
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

    @Nested
    public class NotExecuted {

        @Test
        public void testWithInvocation() {
            crudActions.list().execute(Object.class);
            assertThrows(ActionAssertionError.class, () ->
                    ListActionMatchers.listAction().notExecuted().match(mvcResult)
            );
        }

        @Test
        public void testWithoutInvocation() throws Exception {
            crudActions.list();
            ListActionMatchers.listAction().notExecuted().match(mvcResult);
        }
    }
}
