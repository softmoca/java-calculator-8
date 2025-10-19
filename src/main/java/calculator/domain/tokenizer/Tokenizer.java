package calculator.domain.tokenizer;

import calculator.domain.Delimiters;
import java.util.ArrayList;
import java.util.List;

public final class Tokenizer {
    private Tokenizer() {
    }

    public static List<String> split(String expression, Delimiters delimiters) {
        if (expression == null || expression.isBlank()) {
            return List.of();
        }

        String regex = delimiters.toRegex();
        String[] tokens = expression.split(regex, -1);
        List<String> result = new ArrayList<>(tokens.length);

        for (String token : tokens) {
            result.add(token.trim());
        }

        return result;
    }
}