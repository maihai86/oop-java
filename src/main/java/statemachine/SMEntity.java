package statemachine;

public interface SMEntity {

    String id();

    State state();

    void setState(State state);
}
