package statemachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StateMachine<S extends SMEntity> {

    private Map<State, List<State>> graph = new HashMap<>();
    private Map<State, OnApplyState<S>> executors = new HashMap<>();

    public void onEvent(State state, OnApplyState<S> executor) {
        executors.put(state, executor);
    }

    public abstract void pre(S entity);

    public abstract void post(S entity);

    public final void apply(State state, S entity) throws SMError {
        pre(entity);

        executors.get(state).apply(entity);

        post(entity);
    }
}
