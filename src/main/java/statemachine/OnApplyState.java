package statemachine;

public interface OnApplyState<S extends SMEntity> {

    void apply(S entity);
}
