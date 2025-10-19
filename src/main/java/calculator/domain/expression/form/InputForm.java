package calculator.domain.expression.form;

import calculator.domain.Delimiters;

public interface InputForm {
    Delimiters delimiters();

    String body();
}