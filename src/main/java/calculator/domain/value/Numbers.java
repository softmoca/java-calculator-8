package calculator.domain.value;

import java.util.ArrayList;
import java.util.List;

public final class Numbers {
    private final List<Integer> values;

    private Numbers(List<Integer> values) {
        this.values = List.copyOf(values);
    }

    public static Numbers from(List<String> tokens) {
        List<Integer> ints = new ArrayList<>(tokens.size());
        for (String token : tokens) {
            ints.add(Integer.parseInt(token));
        }
        return new Numbers(ints);
    }

    public int sum() {
        int total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }
}