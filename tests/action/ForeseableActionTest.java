package action;

import static org.junit.Assert.*;

import org.junit.Test;

public class ForeseableActionTest extends ActionTest {

    @Test
    public void foreseeableAction() {
        ForeseableActionMock action = createForeseableAction(2);
        // 2 steps remaining
        assertTrue(action.isReady());
        assertFalse(action.isInProgress());
        assertFalse(action.isFinished());
        action.doStep();
        // 1 step remaining
        assertFalse(action.isReady());
        assertTrue(action.isInProgress());
        assertFalse(action.isFinished());
        action.doStep();
        // 0 step remaining
        assertFalse(action.isReady());
        assertFalse(action.isInProgress());
        assertTrue(action.isFinished());
    }

	@Test
    public void onlyOneValidStateAtEachMoment() {
        this.onlyOneValidStateAtEachMoment1(createForeseableAction(10));
    }

}
