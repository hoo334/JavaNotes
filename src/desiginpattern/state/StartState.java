package desiginpattern.state;

public class StartState implements State {

    @Override
    public void doAction(Context context) {
        System.out.println("Player is in start stage");
        context.setState(this);
    }

    @Override
    public String toString() {
        return "Start State";
    }
}
