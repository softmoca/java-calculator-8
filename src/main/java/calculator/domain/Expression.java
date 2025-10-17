package calculator.domain;

import calculator.domain.policy.BasicDelimiterPolicy;
import calculator.domain.policy.CustomDelimiterPolicy;
import calculator.domain.validate.DefaultValidator;
import calculator.domain.validate.Validator;
import java.util.ArrayList;
import java.util.List;

final class Expression {

    private static final String BASIC_DELIMITERS = ",|:";

    Numbers parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Numbers.zero();
        }

        Delimiters delimiters;
        String body = input;

        // 1) 커스텀 구분자 우선 시도
        CustomDelimiterPolicy custom = new CustomDelimiterPolicy();
        String customRegex = custom.resolve(input); // 커스텀 형식이 아니면 null, 형식 위반이면 예외
        if (customRegex != null) {
            int nl = input.indexOf("\\n");
            body = input.substring(nl + 2);
            if (body.isEmpty()) {
                return Numbers.zero();
            }
            delimiters = new Delimiters(customRegex);
        } else {
            // 2) 기본 구분자 폴백
            BasicDelimiterPolicy basic = new BasicDelimiterPolicy();
            String regex = basic.resolve(input); // 항상 기본 제공
            delimiters = new Delimiters(regex);
        }

        if (body.isEmpty()) {
            return Numbers.zero();
        }

        // 3) 토큰화
        List<String> rawTokens = tokenize(body, delimiters);

        // 4) 트리밍
        List<String> tokens = new ArrayList<String>(rawTokens.size());
        for (int i = 0; i < rawTokens.size(); i++) {
            String t = rawTokens.get(i);
            if (t != null) {
                t = t.trim();
            }
            tokens.add(t);
        }

        // 5) 검증
        Validator validator = new DefaultValidator();
        validator.validate(tokens);

        // 6) 숫자 변환 책임을 Numbers로 이동 (파싱 범위 초과 메시지 일원화)
        return Numbers.from(tokens);
    }

    private List<String> tokenize(String input, Delimiters delimiters) {
        String[] parts = input.split(delimiters.regex(), -1);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < parts.length; i++) {
            list.add(parts[i]);
        }
        return list;
    }
}
