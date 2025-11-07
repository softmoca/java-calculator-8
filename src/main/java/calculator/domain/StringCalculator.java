package calculator.domain;

public class StringCalculator {

    public int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        Expression expression = Expression.from(input);

        String[] tokens = expression.getDelimiter()
                .split(expression.getNumbersText());

        Numbers numbers = Numbers.from(tokens);

        return numbers.sum();
    }
}
