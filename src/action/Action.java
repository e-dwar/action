package action;

public abstract class Action {

    public abstract boolean isReady ();
    public abstract boolean isFinished ();
    public abstract void doStep ();

    public boolean isInProgress () {
        return !isReady() && !isFinished();
    }
}
