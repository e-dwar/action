package action;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class ActionTest {
    
    protected abstract Action createAction();

    protected ForeseableAction createForeseableAction(int timeToEnd) {
        return new ForeseableAction(timeToEnd);
    }

    public void onlyOneValidStateAtEachMoment1(Action action) throws ActionFinishedException {
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

    @Test(expected=ActionFinishedException.class, timeout=2000)
    public void doStepWhileFinishedThrowsException() throws ActionFinishedException {
        Action action = createAction();
        while (!action.isFinished()) {
            try {
                action.doStep();
            } catch (ActionFinishedException e) {
                fail("action was not supposed to be finished, we just checked");
            }
        }
        assertTrue(action.isFinished());
        action.doStep();
    }

}
