package statemachine;

public class Reward implements SMEntity {

    private String id;

    private State state;

    public Reward(String id, State state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }
}
