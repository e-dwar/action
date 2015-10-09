package action;

public abstract class ForeseableAction extends Action {

    protected final int totalTime;
    protected int remainingTime;
    
    public ForeseableAction (int timeToEnd) {
        this.totalTime = timeToEnd;
        this.remainingTime = timeToEnd;
    }

    public boolean isReady () {
        return remainingTime == totalTime;
    }

    public boolean isFinished () {
        return remainingTime <= 0;
    }

    public void doStep () {
        remainingTime--;
    }
}