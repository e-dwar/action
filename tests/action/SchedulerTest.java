package action;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class SchedulerTest extends ActionTest {

	protected abstract Scheduler createScheduler();

	protected Scheduler createScheduler(Action action) {
		Scheduler scheduler = createScheduler();
		scheduler.addAction(action);
		return scheduler;
	}
	
	@Test
    public void onlyOneValidStateAtEachMoment() {
		Scheduler scheduler = createScheduler();
        scheduler.addAction(createForeseableAction(1));
        this.onlyOneValidStateAtEachMoment1(scheduler);
    }
	
	@Test
	public void with1OneStepAction () {
		OneStepAction action1 = new OneStepAction();
		Scheduler scheduler = createScheduler(action1);
        assertFalse(scheduler.isFinished());
        assertFalse(action1.isFinished());
        scheduler.doStep();
        assertTrue(scheduler.isFinished());
        assertTrue(action1.isFinished());
	}

}
