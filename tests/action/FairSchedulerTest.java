package action;

import static org.junit.Assert.assertTrue;

public class FairSchedulerTest extends SchedulerTest {

	@Override
	protected Scheduler createScheduler() {
		return new FairScheduler();
	}

    public void schedulerStep2() {
        assertTrue(action1.isInProgress());
        assertTrue(action2.isFinished());
    }

    public void schedulerWithSchedulerStep2() {
        assertTrue(action1.isInProgress());
        assertTrue(action2.isFinished());
        assertTrue(subScheduler.isInProgress());
    }

}
