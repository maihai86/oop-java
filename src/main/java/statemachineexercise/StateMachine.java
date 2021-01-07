package statemachineexercise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StateMachine<S extends SMEntity> {

    private final Map<State, List<State>> graph;
    private Map<State, OnApplyEvent<S>> executors = new HashMap<>();

    protected StateMachine(Map<State, List<State>> graph) {
        this.graph = graph;
    }

    private void onEvent(State completedState, S entity) {
        if (executors.containsKey(completedState)) {
            executors.get(completedState).execute(entity);
        }
    }

    public final void apply(State state, S entity) throws SMError {
        if (entity.state() == null || !graph.containsKey(entity.state())) {
            throw new SMError("entity's state not contains in graph");
        }
        if (graph.get(entity.state()).isEmpty() || !graph.get(entity.state()).contains(state)) {
            throw new SMError("entity's state doesn't contain leaves");
        }
        System.out.println(String.format("change state from %s to %s", entity.state().name(), state.name()));
        entity.setState(state);
        onEvent(state, entity);
    }

    public void registerExecutor(State state, OnApplyEvent<S> listener) {
        executors.put(state, listener);
    }
}
