package calculator.domain.validator;

import java.util.List;

public final class PositiveLongValidator {
    private PositiveLongValidator() {
    }

    public static void validate(List<String> tokens) {
        for (String t : tokens) {
            // 1) 빈 토큰 검증
            if (t.isEmpty()) {
                throw new IllegalArgumentException("빈 숫자는 허용되지 않습니다.");
            }

            // 2) 숫자 형식 검증
            if (!isNumeric(t)) {
                throw new IllegalArgumentException("양의 정수만 허용됩니다: " + t);
            }

            // 3) 선행 0 검증
            if (t.length() > 1 && t.charAt(0) == '0') {
                throw new IllegalArgumentException("선행 0은 허용되지 않습니다: " + t);
            }

            // 4) long 파싱 및 범위 검증
            try {
                long n = Long.parseLong(t);
                if (n <= 0) {
                    throw new IllegalArgumentException("양의 정수만 허용됩니다: " + t);
                }
            } catch (NumberFormatException e) {
                // long 범위 초과
                throw new IllegalArgumentException("정수 범위를 초과했습니다: " + t);
            }
        }
    }

    private static boolean isNumeric(String s) {
        if (s.isEmpty()) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}