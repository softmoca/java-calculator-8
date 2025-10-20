package calculator.domain.expression.form;

import calculator.domain.Delimiters;

public record BasicForm(String body) implements InputForm {

    @Override
    public Delimiters delimiters() {
        return Delimiters.basic();
    }
}