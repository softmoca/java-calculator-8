package calculator.domain;

public class Calculator {

    public long sumOf(String input) {
        if (input == null || input.isEmpty()) {
            return 0L;
        }

        Expression expression = new Expression();
        Numbers numbers = expression.parse(input);
        return numbers.sum();
    }
}
