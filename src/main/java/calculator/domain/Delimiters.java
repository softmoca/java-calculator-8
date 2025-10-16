package calculator.domain;

public final class Delimiters {

    private final String regex;

    Delimiters(String regex) {
        this.regex = regex;
    }

    String regex() {
        return this.regex;
    }
}
