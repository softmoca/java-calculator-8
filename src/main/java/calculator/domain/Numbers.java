package calculator.domain;

import java.util.ArrayList;
import java.util.List;

public final class Numbers {

    private final List<Long> values;

    public Numbers(List<Long> values) {
        this.values = values;
    }

    public static Numbers from(List<String> tokens) {
        if (tokens == null) {
            throw new IllegalArgumentException("토큰 목록이 없습니다.");
        }
        List<Long> nums = new ArrayList<Long>(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            String t = tokens.get(i);
            try {
                long v = Long.parseLong(t);
                nums.add(v);
            } catch (NumberFormatException e) {
                // validator를 통과했지만 Long 범위를 넘는 경우 등
                throw new IllegalArgumentException(
                        "정수 범위를 초과했습니다: token=" + t
                );
            }
        }
        return new Numbers(nums);
    }

    // ✅ 빈 입력 대응
    public static Numbers zero() {
        return new Numbers(new ArrayList<Long>(0));
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
