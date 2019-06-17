package ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base;

import ch.puzzle.libpuzzle.springframework.boot.rest.test.actionmatcher.base.exception.ActionAssertionError;
import junit.framework.ComparisonFailure;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

@FunctionalInterface
public interface ActionResultMatcher extends ResultMatcher {

    void match(ActionResult result) throws Exception;

    default void match(MvcResult result) throws Exception {
        try {
            match(new ActionResult(result));
        } catch (ComparisonFailure e) {
            throw ActionAssertionError.wrongActionParams(e);
        } catch (WantedButNotInvoked e) {
            throw ActionAssertionError.missingActionExecution();
        }
    }
}


