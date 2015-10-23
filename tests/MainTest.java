import static org.junit.Assert.*;

import org.junit.Test;

import action.*;

public class MainTest {

    private SequentialScheduler createScheduler() {
        return new SequentialScheduler();
    }

    private ForeseableAction createForeseableAction(int timeToEnd) {
        return new ForeseableAction(timeToEnd);
    }

    @Test
    public void foreseeableAction() {
        ForeseableAction action = createForeseableAction(2);
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
    public void scheduler() {
        ForeseableAction action1 = createForeseableAction(2);
        ForeseableAction action2 = createForeseableAction(1);
        SequentialScheduler scheduler = createScheduler();
        scheduler.addAction(action1);
        scheduler.addAction(action2);
        assertTrue(action1.isReady());
        assertTrue(action2.isReady());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isReady());
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isReady());
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isFinished());
    }

    @Test
    public void schedulerWithScheduler() {
        ForeseableAction action1 = createForeseableAction(2);
        SequentialScheduler subScheduler = createScheduler();
        SequentialScheduler scheduler = createScheduler();
        subScheduler.addAction(action1);
        scheduler.addAction(subScheduler);
        assertTrue(action1.isReady());
        assertTrue(subScheduler.isReady());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(subScheduler.isInProgress());
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(subScheduler.isFinished());
    }

    @Test
    public void onlyOneValidStateAtEachMomentForForeseebleAction() {
        onlyOneValidStateAtEachMoment(createForeseableAction(10));
    }

    @Test
    public void onlyOneValidStateAtEachMomentForScheduler() {
    	SequentialScheduler scheduler = createScheduler();
        scheduler.addAction(createForeseableAction(1));
        onlyOneValidStateAtEachMoment(scheduler);
    }

    protected void onlyOneValidStateAtEachMoment(Action action) {
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