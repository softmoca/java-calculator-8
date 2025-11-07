package calculator;

public class StringCalculator {

    public int calculate(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        Delimiter delimiter = Delimiter.getDefault();
        String[] tokens = delimiter.split(input);

        Numbers numbers = Numbers.from(tokens);

        return numbers.sum();
    }
}
