package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete;


import ch.puzzle.libpuzzle.springframework.boot.rest.RestActions;
import ch.puzzle.libpuzzle.springframework.boot.rest.action.DeleteAction;
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

import static ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.delete.DeleteActionConfigurer.mockedDeleteAction;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeleteActionResultMatchersTest {

    @Mock
    private RestActions<?, ?, ?, ?, DeleteAction<Object>> restActions;

    @Mock
    private MvcResult mvcResult;

    @BeforeEach
    public void setup() {
        final var request = new MockHttpServletRequest();
        doReturn(request).when(mvcResult).getRequest();
        mockedDeleteAction(restActions).beforeMockMvcCreated(null, null).postProcessRequest(request);
    }

    @Nested
    public class IdParameter {

        @Test
        public void testNoInvocation() {
            restActions.delete();
            assertThrows(ActionAssertionError.class, () ->
                    DeleteActionMatchers.deleteAction().by(0).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    DeleteActionMatchers.deleteAction().by(equalTo(0)).match(mvcResult)
            );
        }

        @Test
        public void tesDifferentInvocation() {
            restActions.delete().by(1);
            assertThrows(ActionAssertionError.class, () ->
                    DeleteActionMatchers.deleteAction().by(2).match(mvcResult)
            );
            assertThrows(ActionAssertionError.class, () ->
                    DeleteActionMatchers.deleteAction().by(equalTo(2)).match(mvcResult)
            );
        }

        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.delete().by(1);
            DeleteActionMatchers.deleteAction().by(1).match(mvcResult);
            DeleteActionMatchers.deleteAction().by(equalTo(1)).match(mvcResult);
        }
    }

    @Nested
    public class Executed {

        @Test
        public void testNoInvocation() {
            restActions.delete();
            assertThrows(ActionAssertionError.class, () ->
                    DeleteActionMatchers.deleteAction().executed().match(mvcResult)
            );
        }


        @Test
        public void testCorrectInvocation() throws Exception {
            restActions.delete().execute();
            DeleteActionMatchers.deleteAction().executed().match(mvcResult);
        }
    }
}
