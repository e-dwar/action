import static org.junit.Assert.*;

import org.junit.Test;

import action.*;

public class MainTest {

    private SchedulerMock createScheduler() {
        return new SchedulerMock();
    }

    private ForeseableActionMock createForeseableAction(int timeToEnd) {
        return new ForeseableActionMock(timeToEnd);
    }

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
    public void scheduler() {
        ForeseableActionMock action1 = createForeseableAction(2);
        ForeseableActionMock action2 = createForeseableAction(1);
        SchedulerMock scheduler = createScheduler();
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
        ForeseableActionMock action1 = createForeseableAction(2);
        SchedulerMock subScheduler = createScheduler();
        SchedulerMock scheduler = createScheduler();
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
        SchedulerMock scheduler = createScheduler();
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