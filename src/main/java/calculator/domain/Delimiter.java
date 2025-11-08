package calculator.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Delimiter {
    private static final String DEFAULT_PATTERN = "[,:]";

    private static final Delimiter DEFAULT =
            new Delimiter(DEFAULT_PATTERN);

    private final String pattern;

    private Delimiter(String pattern) {
        this.pattern = pattern;
    }

    public static Delimiter getDefault() {
        return DEFAULT;
    }

    public static Delimiter custom(String delimiter) {
        return new Delimiter(Pattern.quote(delimiter));
    }

    public String[] split(String input) {
        return input.split(pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Delimiter delimiter = (Delimiter) o;
        return Objects.equals(pattern, delimiter.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern);
    }
}
