package action;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class SchedulerTest extends ActionTest {

    protected ForeseableAction action1;
    protected ForeseableAction action2;
    protected Scheduler scheduler;
    protected Scheduler subScheduler;
    protected abstract Scheduler createScheduler();
    protected abstract void schedulerWithSchedulerStep2();
    protected abstract void schedulerStep2();

    protected Scheduler createScheduler(Action action) {
        Scheduler scheduler = createScheduler();
        scheduler.addAction(action);
        return scheduler;
    }

    @Override
    protected Action createAction() {
        return createScheduler(createForeseableAction(1));
    }
    
    @Test
    public void onlyOneValidStateAtEachMoment() throws ActionFinishedException {
        Scheduler scheduler = createScheduler();
        scheduler.addAction(createForeseableAction(1));
        this.onlyOneValidStateAtEachMoment1(scheduler);
    }
    
    @Test
    public void with1OneStepAction () throws ActionFinishedException {
        OneStepAction action1 = new OneStepAction();
        Scheduler scheduler = createScheduler(action1);
        assertFalse(scheduler.isFinished());
        assertFalse(action1.isFinished());
        scheduler.doStep();
        assertTrue(scheduler.isFinished());
        assertTrue(action1.isFinished());
    }

    @Test
    public void scheduler () throws ActionFinishedException {
        action1 = createForeseableAction(2);
        action2 = createForeseableAction(1);
        scheduler = createScheduler();
        scheduler.addAction(action1);
        scheduler.addAction(action2);
        assertTrue(action1.isReady());
        assertTrue(action2.isReady());
        assertTrue(scheduler.isReady());
        // step 1
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isReady());
        assertTrue(scheduler.isInProgress());
        // step 2
        scheduler.doStep();
        this.schedulerStep2();
        assertTrue(scheduler.isInProgress());
        // step 3
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isFinished());
        assertTrue(scheduler.isFinished());
    }

    @Test
    public void schedulerWithScheduler() throws ActionFinishedException {
        action1 = createForeseableAction(2);
        action2 = createForeseableAction(1);
        subScheduler = createScheduler();
        scheduler = createScheduler();
        scheduler.addAction(subScheduler);
        subScheduler.addAction(action1);
        scheduler.addAction(action2);
        // initial state
        assertTrue(action1.isReady());
        assertTrue(action2.isReady());
        assertTrue(subScheduler.isReady());
        assertTrue(scheduler.isReady());
        // step 1
        scheduler.doStep();
        assertTrue(action1.isInProgress());
        assertTrue(action2.isReady());
        assertTrue(subScheduler.isInProgress());
        assertTrue(scheduler.isInProgress());
        // step 2
        scheduler.doStep();
        this.schedulerWithSchedulerStep2();
        assertTrue(scheduler.isInProgress());
        // step 3
        scheduler.doStep();
        assertTrue(action1.isFinished());
        assertTrue(action2.isFinished());
        assertTrue(subScheduler.isFinished());
        assertTrue(scheduler.isFinished());
    }

}
