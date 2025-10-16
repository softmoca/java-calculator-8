package calculator.domain.policy;


public final class CustomDelimiterPolicy implements DelimiterPolicy {

    @Override
    public String resolve(String input) {
        if (input == null) {
            return null;
        }
        if (!input.startsWith("//")) {
            return null; // 커스텀 선언부가 아니면 이 정책 비적용
        }

        int nl = input.indexOf("\\n");
        if (nl < 0) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다: //X\\n...");
        }

        String customRegex = input.substring(2, nl);
        if (customRegex.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 단일 문자여야 합니다: " + customRegex);
        }

        char c = customRegex.charAt(0);
        if (Character.isDigit(c)) {
            throw new IllegalArgumentException("커스텀 구분자가 숫자일 수 없습니다: " + c);
        }

        return customRegex;
    }
}
