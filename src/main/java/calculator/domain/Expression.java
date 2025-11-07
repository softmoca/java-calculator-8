package calculator.domain;

public class Expression {
    private static final String CUSTOM_PREFIX = "//";
    private static final String CUSTOM_SUFFIX = "\\n";

    private final Delimiter delimiter;
    private final String numbersText;

    private Expression(Delimiter delimiter, String numbersText) {
        this.delimiter = delimiter;
        this.numbersText = numbersText;
    }

    public static Expression from(String input) {

        if (input.isEmpty()) {
            return new Expression(
                    Delimiter.getDefault(),
                    input
            );
        }

        if (input.startsWith(CUSTOM_PREFIX)) {
            return parseCustomFormat(input);
        }

        return new Expression(
                Delimiter.getDefault(),
                input
        );
    }

    private static Expression parseCustomFormat(String input) {

        int delimiterEndIndex = input.indexOf(CUSTOM_SUFFIX);

        String customDelimiter = input.substring(2, delimiterEndIndex);

        String numbersText = input.substring(delimiterEndIndex + 2);

        return new Expression(
                Delimiter.custom(customDelimiter),
                numbersText
        );
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }

    public String getNumbersText() {
        return numbersText;
    }
}
