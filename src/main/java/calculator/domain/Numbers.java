package calculator.domain;

import java.util.List;

public final class Numbers {

    private final List<Long> values;

    public Numbers(List<Long> values) {
        this.values = values;
    }

    public long sum() {
        long result = 0L;
        for (int i = 0; i < values.size(); i++) {
            long next = values.get(i);
            try {
                result = Math.addExact(result, next);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("정수 범위를 초과했습니다: sum overflow");
            }
        }
        return result;
    }
}
