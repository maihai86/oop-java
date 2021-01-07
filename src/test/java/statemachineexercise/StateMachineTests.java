package statemachineexercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StateMachineTests {

    static class TestState implements State {

        private String id;
        private String name;

        public TestState(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestState testState = (TestState) o;
            return name.equals(testState.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static class TestStateMachine extends StateMachine<TestSMEntity> {

        protected TestStateMachine(Map<State, List<State>> graph) {
            super(graph);
        }
    }

    static class TestSMEntity implements SMEntity {

        private State state;

        public TestSMEntity(State state) {
            this.state = state;
        }

        @Override
        public String id() {
            return "1";
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

    @Test
    public void test1() throws SMError {
        Map<State, List<State>> graph = new HashMap<>();
        TestState state1 = new TestState("1", "start");
        TestState state2 = new TestState("2", "in progress");
        TestState state3 = new TestState("3", "canceled");
        graph.put(state1, Arrays.asList(state2, state3));
        StateMachine stateMachine = new TestStateMachine(graph);

        SMEntity entity = new TestSMEntity(new TestState("5", "start"));
        stateMachine.apply(new TestState("4", "in progress"), entity);

        Assertions.assertEquals("in progress", entity.state().name());
    }

    @Test
    public void test_then_execute_other() throws SMError {
        Map<State, List<State>> graph = new HashMap<>();
        TestState state1 = new TestState("1", "start");
        TestState state2 = new TestState("2", "in progress");
        TestState state3 = new TestState("3", "canceled");
        graph.put(state1, Arrays.asList(state2, state3));

        Map<State, OnApplyEvent<TestSMEntity>> executors = new HashMap<>();
        final String[] writeMeToComplete = new String[]{"abc"};
        StateMachine stateMachine = new TestStateMachine(graph);
        stateMachine.registerExecutor(new TestState("7", "in progress"),
                entity -> {
                    writeMeToComplete[0] = "in progress";
                });

        SMEntity entity = new TestSMEntity(new TestState("5", "start"));
        stateMachine.apply(new TestState("4", "in progress"), entity);

        Assertions.assertEquals("in progress", entity.state().name());
        Assertions.assertEquals("in progress", writeMeToComplete[0]);
    }
}
