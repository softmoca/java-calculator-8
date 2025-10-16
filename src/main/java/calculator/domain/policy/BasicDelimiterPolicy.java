package calculator.domain.policy;

public final class BasicDelimiterPolicy implements DelimiterPolicy {

    private static final String BASIC_REGEX = ",|:";

    @Override
    public String resolve(String input) {
        return BASIC_REGEX;
    }
}
