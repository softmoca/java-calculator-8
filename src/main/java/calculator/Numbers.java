package calculator;

import java.util.Arrays;
import java.util.List;

public class Numbers {
    private final List<Integer> values;

    private Numbers(List<Integer> values) {
        this.values = values;
    }

    public static Numbers from(String[] tokens) {

        List<Integer> numbers = Arrays.stream(tokens)
                .map(Integer::parseInt)
                .toList();

        return new Numbers(numbers);
    }

    public int sum() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
