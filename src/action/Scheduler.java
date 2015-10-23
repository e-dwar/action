package action;

import java.util.ArrayList;

public abstract class Scheduler extends Action {
   
    protected boolean isReady = true;
    protected boolean isInitialized = false;
    protected final ArrayList<Action> actions = new ArrayList<Action>();
    protected abstract Action next ();
    
    public Scheduler () {
        super();
    }

    public boolean isReady () {
        return isInitialized && isReady;
    }

    public boolean isInProgress () {
        return isInitialized && super.isInProgress();
    }

    public boolean isFinished () {
        return isInitialized && !isReady() && actions.isEmpty();
    }
    
    public void doStep () throws ActionFinishedException {
    	super.doStep();
		isReady = false;
		Action nextAction = next();
		nextAction.doStep();
		if (nextAction.isFinished()) {
			actions.remove(nextAction);
		}    	
    }

    public void addAction (Action subAction) {
        isInitialized = true;
        if (subAction.isFinished()) {
            throw new IllegalArgumentException(
                "Can't add an already finished action"
            );
        }
        if (isFinished()) {
            throw new IllegalStateException(
                "You can't add an action to a finished scheduler"
            );
        }
        else {
            actions.add(subAction);
        }
    }
}