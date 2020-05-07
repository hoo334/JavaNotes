package desiginpattern.state;

public class Context {
    private State state;

    public Context() {
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
