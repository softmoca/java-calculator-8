package calculator.domain;

import calculator.domain.policy.BasicDelimiterPolicy;
import calculator.domain.policy.CustomDelimiterPolicy;
import java.util.ArrayList;
import java.util.List;

final class Expression {

    private static final String BASIC_DELIMITERS = ",|:";

    Numbers parse(String input) {
        Delimiters delimiters;
        String body = input;

        // 1) 커스텀 구분자 우선 시도
        CustomDelimiterPolicy custom = new CustomDelimiterPolicy();
        String customRegex = custom.resolve(input); // 커스텀 형식이 아니면 null, 형식 위반이면 예외
        if (customRegex != null) {
            int nl = input.indexOf("\\n");
            body = input.substring(nl + 2);
            if (body.isEmpty()) {
                throw new IllegalArgumentException("커스텀 구분자 선언 이후 본문이 비어 있습니다.");
            }
            delimiters = new Delimiters(customRegex);
        } else {
            // 2) 기본 구분자 폴백
            BasicDelimiterPolicy basic = new BasicDelimiterPolicy();
            String regex = basic.resolve(input); // 항상 기본 제공
            delimiters = new Delimiters(regex);
        }

        List<String> tokens = tokenize(body, delimiters);
        List<Long> numbers = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            token = token.trim();

            if (token.isEmpty()) {
                throw new IllegalArgumentException("비어 있는 토큰은 허용되지 않습니다: index=" + i);
            }
            try {
                numbers.add(Long.parseLong(token));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
            }
        }
        return new Numbers(numbers);
    }

    private List<String> tokenize(String input, Delimiters delimiters) {
        String[] parts = input.split(delimiters.regex());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            list.add(parts[i]);
        }
        return list;
    }
}
