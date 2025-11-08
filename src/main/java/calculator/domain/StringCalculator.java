package calculator.domain;

public class StringCalculator {

    public int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        Expression expression = Expression.from(input);
        Numbers numbers = expression.toNumbers();
        return numbers.sum();
    }
}
