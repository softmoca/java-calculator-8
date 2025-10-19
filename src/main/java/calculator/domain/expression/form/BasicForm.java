package calculator.domain.expression.form;

import calculator.domain.Delimiters;

public class BasicForm implements InputForm {
    private final String body;

    public BasicForm(String body) {
        this.body = body;
    }

    @Override
    public Delimiters delimiters() {
        return Delimiters.basic();
    }

    @Override
    public String body() {
        return body;
    }
}