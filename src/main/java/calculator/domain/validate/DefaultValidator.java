package calculator.domain.validate;

import java.util.List;

public final class DefaultValidator implements Validator {

    @Override
    public void validate(List<String> tokens) {
        if (tokens == null) {
            throw new IllegalArgumentException("토큰 목록이 없습니다.");
        }

        for (int i = 0; i < tokens.size(); i++) {
            String t = tokens.get(i);

            // 1) 빈 토큰 금지
            if (t == null || t.isEmpty()) {
                throw new IllegalArgumentException("비어 있는 토큰은 허용되지 않습니다: index=" + i);
            }

            // 2) 음수 금지 (가장 먼저 검사)
            if (t.startsWith("-")) {
                throw new IllegalArgumentException("0 또는 음수는 허용되지 않습니다: " + t);
            }

            // 3) 숫자만 허용 (부호/문자 금지)
            for (int j = 0; j < t.length(); j++) {
                char ch = t.charAt(j);
                if (ch < '0' || ch > '9') {
                    throw new IllegalArgumentException("양의 정수만 허용됩니다: " + t);
                }
            }

            // 4) 선행 0 불허 (길이>1 이고 '0' 시작)
            if (t.length() > 1 && t.charAt(0) == '0') {
                throw new IllegalArgumentException("선행 0은 허용되지 않습니다: " + t);
            }

            // 5) 0 금지
            if ("0".equals(t)) {
                throw new IllegalArgumentException("0 또는 음수는 허용되지 않습니다: 0");
            }
        }
    }
}
