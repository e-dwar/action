package action;

public abstract class Action {

    public abstract boolean isReady ();
    public abstract boolean isFinished ();
    
    public void doStep () throws ActionFinishedException {
    	if (isFinished()) {
    		throw new ActionFinishedException();
    	}
    }

    public boolean isInProgress () {
        return !isReady() && !isFinished();
    }
}
