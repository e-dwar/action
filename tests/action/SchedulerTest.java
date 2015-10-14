package action;

import static org.junit.Assert.*;

import org.junit.Test;

public class SchedulerTest extends ActionTest {

	@Test
    protected void onlyOneValidStateAtEachMoment(Action action) {
        SchedulerMock scheduler = createScheduler();
        scheduler.addAction(createForeseableAction(1));
        super.onlyOneValidStateAtEachMoment(scheduler);
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

}
