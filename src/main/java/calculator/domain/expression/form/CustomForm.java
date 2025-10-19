package calculator.domain.expression.form;

import calculator.domain.Delimiters;

public class CustomForm implements InputForm {
    private final Delimiters delims;
    private final String body;

    public CustomForm(Delimiters delims, String body) {
        this.delims = delims;
        this.body = body;
    }

    @Override
    public Delimiters delimiters() {
        return delims;
    }

    @Override
    public String body() {
        return body;
    }
}