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
        if (delimiterEndIndex != 3) {
            throw new IllegalArgumentException("[ERROR] 커스텀구분자 형식이 잘못되었습니다.");
        }

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

    public Numbers toNumbers() {
        String[] tokens = delimiter.split(numbersText);
        return Numbers.from(tokens);
    }
}
