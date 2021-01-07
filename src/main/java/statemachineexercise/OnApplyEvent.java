package statemachineexercise;

public interface OnApplyEvent<S extends SMEntity> {

    void execute(S entity);
}
