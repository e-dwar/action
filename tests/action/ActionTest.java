package action;

import static org.junit.Assert.*;

public abstract class ActionTest {

    protected ForeseableActionMock createForeseableAction(int timeToEnd) {
        return new ForeseableActionMock(timeToEnd);
    }

    public void onlyOneValidStateAtEachMoment1(Action action) {
        assertTrue(action.isReady());
        assertFalse(action.isInProgress());
        assertFalse(action.isFinished());
        while (!action.isFinished()) {
            action.doStep();
            assertFalse(action.isReady());
            // isFinished xor isInProgress
            assertTrue(action.isFinished() == !action.isInProgress());
        }
        assertFalse(action.isReady());
        assertFalse(action.isInProgress());
        assertTrue(action.isFinished());
    }

}
