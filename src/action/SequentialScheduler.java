package action;

public class SequentialScheduler extends Scheduler {

    public void doStep () {
        isReady = false;
        Action nextAction = actions.get(0);
        nextAction.doStep();
        if (nextAction.isFinished()) {
            actions.remove(0);
        }
    }
}
