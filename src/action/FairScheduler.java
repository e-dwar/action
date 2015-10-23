package action;

public class FairScheduler extends Scheduler {

	protected int i = 0;
	
	protected Action next () {
		Action action = actions.get(i);
		i = ++i % actions.size();
		return action;
	}
}
