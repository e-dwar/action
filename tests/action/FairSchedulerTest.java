package action;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FairSchedulerTest extends SchedulerTest {

	@Override
	protected Scheduler createScheduler() {
		return new FairScheduler();
	}

    @Test
    public void scheduler() {
        ForeseableAction action1 = createForeseableAction(2);
        ForeseableAction action2 = createForeseableAction(1);
        Scheduler scheduler = createScheduler();
        scheduler.addAction(action1);
        scheduler.addAction(action2);
        assertTrue(action1.isReady());
        assertTrue(action2.isReady());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isReady());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isFinished());
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isFinished());
    }

    @Test
    public void schedulerWithScheduler() {
        ForeseableAction action1 = createForeseableAction(2);
        ForeseableAction action2 = createForeseableAction(1);
        Scheduler subScheduler = createScheduler();
        Scheduler scheduler = createScheduler();
        subScheduler.addAction(action1);
        scheduler.addAction(subScheduler);
    	scheduler.addAction(action2);
        assertTrue(action1.isReady());
        assertTrue(action2.isReady());
        assertTrue(subScheduler.isReady());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isReady());
        assertTrue(subScheduler.isInProgress());
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isFinished());
        assertTrue(subScheduler.isInProgress());
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isFinished());
        assertTrue(subScheduler.isFinished());
    }

}
