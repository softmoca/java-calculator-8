package calculator.domain.expression.recognizer;

import calculator.domain.expression.form.InputForm;
import java.util.Optional;

public interface InputFormRecognizer {
    Optional<InputForm> recognize(String raw);
}