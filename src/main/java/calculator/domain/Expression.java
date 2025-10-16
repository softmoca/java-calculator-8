package calculator.domain;

import java.util.ArrayList;
import java.util.List;

final class Expression {

    private static final String BASIC_DELIMITERS = ",|:";

    Numbers parse(String input) {
        Delimiters delimiters = new Delimiters(BASIC_DELIMITERS);
        List<String> tokens = tokenize(input, delimiters);
        List<Long> numbers = new ArrayList<>();

        for (String token : tokens) {
            token = token.trim();
            if (!token.isEmpty()) {
                try {
                    numbers.add(Long.parseLong(token));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다: " + token);
                }
            } else {
                throw new IllegalArgumentException("비어 있는 토큰은 허용되지 않습니다.");
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
