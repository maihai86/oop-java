package statemachine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachineTests {

    @Test
    public void apply_success() throws SMError {
        List<State> defines = new ArrayList<>();
        HistoryItemState pending = new HistoryItemState("pending", "0");
        HistoryItemState completed = new HistoryItemState("completed", "1");
        defines.add(completed);
        defines.add(new HistoryItemState("finished", "2"));

        Map<State, List<State>> config = new HashMap<>();
        config.put(pending, defines);
        RewardSM rewardSM = new RewardSM(config);
        rewardSM.onEvent(pending, new OnApplyState<Reward>() {
            @Override
            public void apply(Reward entity) {
                entity.setState(completed);
            }
        });
        Reward reward = new Reward("0", pending);
        rewardSM.apply(pending, reward);
        Assertions.assertTrue(reward.state() == completed);
    }
}
