package calculator.domain;

import java.util.Arrays;
import java.util.List;

public class Numbers {
    private final List<Integer> values;

    private Numbers(List<Integer> values) {
        this.values = List.copyOf(values);
    }

    public static Numbers from(String[] tokens) {
        List<Integer> numbers = Arrays.stream(tokens)
                .map(Numbers::parseAndValidate)
                .toList();
        return new Numbers(numbers);
    }

    private static int parseAndValidate(String token) {
        validateToken(token);
        int value = parseInt(token.trim());
        validateNonNegative(value);
        return value;
    }

    private static void validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 값은 허용되지 않습니다");
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자가 아닌 값: " + s);
        }
    }

    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("[ERROR] 음수는 허용되지 않습니다: " + n);
        }
    }

    public int sum() {
        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
