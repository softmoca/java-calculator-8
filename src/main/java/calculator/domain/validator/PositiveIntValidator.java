package calculator.domain.validator;

import java.util.List;

public final class PositiveIntValidator {
    private PositiveIntValidator() {
    }

    public static void validate(List<String> tokens) {
        for (String t : tokens) {
            if (t.isEmpty()) {
                throw new IllegalArgumentException("빈 숫자는 허용되지 않습니다.");
            }
            if (!isInteger(t)) {
                throw new IllegalArgumentException("양의 정수만 허용됩니다: " + t);
            }
            if (t.length() > 1 && t.charAt(0) == '0') {
                throw new IllegalArgumentException("선행 0은 허용되지 않습니다: " + t);
            }
            int n = Integer.parseInt(t);
            if (n <= 0) {
                throw new IllegalArgumentException("양의 정수만 허용됩니다: " + t);
            }
        }
    }

    private static boolean isInteger(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return !s.isEmpty();
    }
}