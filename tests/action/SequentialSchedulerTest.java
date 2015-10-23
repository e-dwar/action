package action;

import static org.junit.Assert.assertTrue;

public class SequentialSchedulerTest extends SchedulerTest {

	@Override
	protected Scheduler createScheduler() {
		return new SequentialScheduler();
	}

    public void schedulerStep2() {
        assertTrue(action1.isFinished());
        assertTrue(action2.isReady());
    }

    public void schedulerWithSchedulerStep2() {
        assertTrue(action1.isFinished());
        assertTrue(action2.isReady());
        assertTrue(subScheduler.isFinished());
    }

}
