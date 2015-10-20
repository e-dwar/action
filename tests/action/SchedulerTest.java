package action;

import org.junit.Test;

public abstract class SchedulerTest extends ActionTest {

	protected abstract Scheduler createScheduler();
	
	@Test
    public void onlyOneValidStateAtEachMoment() {
		Scheduler scheduler = createScheduler();
        scheduler.addAction(createForeseableAction(1));
        this.onlyOneValidStateAtEachMoment1(scheduler);
    }

}
