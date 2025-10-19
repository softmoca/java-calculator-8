package calculator.domain.expression.recognizer;

import calculator.domain.expression.form.BasicForm;
import calculator.domain.expression.form.InputForm;
import java.util.Optional;

public class BasicFormRecognizer implements InputFormRecognizer {
    @Override
    public Optional<InputForm> recognize(String raw) {
        return Optional.of(new BasicForm(raw));
    }
}