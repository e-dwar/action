package action;

public class FairScheduler extends Scheduler {

	protected int i;

	public FairScheduler() {
		super();
		i = 0;
	}

	public void doStep() {
		isReady = false;
		Action nextAction = actions.get(i);
		nextAction.doStep();
		if (nextAction.isFinished()) {
			actions.remove(i);
		}
		else {
			i++;
		}
		if (actions.size() > 0) {
			i = i % actions.size();
		}
	}
}
