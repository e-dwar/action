package action;

public class SequentialScheduler extends Scheduler {
    
    protected Action next () {
        return actions.get(0);
    }
}
