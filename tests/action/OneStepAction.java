package action;

public class OneStepAction extends Action {
	
	private boolean isFinished = false;
	
	public boolean isFinished () {
		return isFinished;
	}

	public boolean isReady() {
		return !isFinished;
	}
	
	public void doStep () {
		isFinished = true;
	}
}
