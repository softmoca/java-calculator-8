package calculator.domain.value;

import java.util.ArrayList;
import java.util.List;

public final class Numbers {
    private final List<Long> values;

    private Numbers(List<Long> values) {
        this.values = List.copyOf(values);
    }

    public static Numbers from(List<String> tokens) {
        List<Long> ints = new ArrayList<>(tokens.size());
        for (String token : tokens) {
            ints.add(Long.parseLong(token));
        }
        return new Numbers(ints);
    }

    public long sum() {
        long total = 0L;
        for (long value : values) {
            try {
                total = Math.addExact(total, value);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("합계가 정수 범위를 초과했습니다.");
            }
        }
        return total;
    }
}