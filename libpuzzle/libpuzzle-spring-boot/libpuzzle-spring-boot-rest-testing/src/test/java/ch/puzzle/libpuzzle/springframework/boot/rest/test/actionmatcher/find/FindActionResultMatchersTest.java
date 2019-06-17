package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.FindAction;
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

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.find.FindActionConfigurer.mockedFindAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindActionResultMatchersTest {

    @Mock
    private RestActions<?, FindAction<Object, Object>, ?, ?, ?> restActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedFindAction(restActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class IdParameter {

        @Test
        public void testNoInvocation() {
            restActions.find();
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().by(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().by(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            restActions.find().by(1);
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().by(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().by(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.find().by(1);
            FindActionMatchers.findAction().by(1).match(mvcResult);
            FindActionMatchers.findAction().by(equalTo(1)).match(mvcResult);
        }
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            restActions.find();
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().executed().match(mvcResult)
            );
        }


        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.find().execute(Object.class);
            FindActionMatchers.findAction().executed().match(mvcResult);
        }
    }

    @Nested
    public class ExecutedWithResponseType {

        @Test
        public void testNoInvocation() {
            restActions.find();
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testDifferentInvocation() {
            restActions.find().execute(String.class);
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().executed(Object.class).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    FindActionMatchers.findAction().executed(equalTo(Object.class)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.find().execute(Object.class);
            FindActionMatchers.findAction().executed(Object.class).match(mvcResult);
            FindActionMatchers.findAction().executed(equalTo(Object.class)).match(mvcResult);
        }
    }
}
