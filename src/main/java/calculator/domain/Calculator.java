package calculator.domain;

public class Calculator {

    public long sumOf(String input) {
        Expression expression = new Expression();
        Numbers numbers = expression.parse(input);
        return numbers.sum();
    }
}
