package statemachine;

public class HistoryItemState implements State {

    private String name;

    private String id;

    public HistoryItemState(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
