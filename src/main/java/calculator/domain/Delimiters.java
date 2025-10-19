package calculator.domain;

import java.util.Set;

public final class Delimiters {
    private final Set<String> values;


    private Delimiters(Set<String> values) {
        this.values = Set.copyOf(values);
    }

    public static Delimiters basic() {
        return new Delimiters(Set.of(",", ":"));
    }

    public static Delimiters from(char c) {
        return new Delimiters(Set.of(String.valueOf(c)));
    }


    public String toRegex() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String s : values) {
            if (!first) {
                sb.append("|");
            }
            sb.append(escape(s));
            first = false;
        }

        return sb.toString();
    }

    private static String escape(String s) {
        if (s.matches("[\\\\^$.|?*+()\\[\\]{}]")) {
            return "\\" + s;
        }
        return s;
    }


}
