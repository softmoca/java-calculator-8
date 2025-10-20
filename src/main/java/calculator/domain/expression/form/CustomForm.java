package calculator.domain.expression.form;

import calculator.domain.Delimiters;

public record CustomForm(Delimiters delims, String body) implements InputForm {

    @Override
    public Delimiters delimiters() {
        return delims;
    }
}