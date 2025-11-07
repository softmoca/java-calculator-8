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
                .map(token -> {
                    if (token.trim().isEmpty()) {
                        throw new IllegalArgumentException(
                                "[ERROR] 빈 값은 허용되지 않습니다"
                        );
                    }

                    return Integer.parseInt(token.trim());

                })
                .peek(n -> {
                    if (n < 0) {
                        throw new IllegalArgumentException(
                                "[ERROR] 음수는 허용되지 않습니다: " + n
                        );
                    }
                })
                .toList();

        return new Numbers(numbers);
    }

    public int sum() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
