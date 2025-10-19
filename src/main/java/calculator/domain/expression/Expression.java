package calculator.domain.expression;

import calculator.domain.Delimiters;
import calculator.domain.expression.form.InputForm;
import java.util.Objects;

public final class Expression {
    private final Delimiters delimiters;
    private final String payload;

    private Expression(Delimiters delimiters, String payload) {
        this.delimiters = Objects.requireNonNull(delimiters);
        this.payload = payload;
    }

    public static Expression from(String raw) {
        InputForm form = new InputFormFactory().of(raw);
        return new Expression(form.delimiters(), form.body());
    }

    public Delimiters delimiters() {
        return delimiters;
    }

    public String payload() {
        return payload;
    }
}