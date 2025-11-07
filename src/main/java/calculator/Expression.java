package calculator;

public class Expression {
    private static final String CUSTOM_PREFIX = "//";
    private static final String CUSTOM_SUFFIX = "\n";

    private final Delimiter delimiter;
    private final String numbersText;

    private Expression(Delimiter delimiter, String numbersText) {
        this.delimiter = delimiter;
        this.numbersText = numbersText;
    }

    public static Expression from(String input) {

        return new Expression(
                Delimiter.getDefault(),
                input
        );
    }


    public Delimiter getDelimiter() {
        return delimiter;
    }

    public String getNumbersText() {
        return numbersText;
    }
}
