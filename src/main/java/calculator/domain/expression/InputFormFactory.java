package calculator.domain.expression;

import calculator.domain.expression.form.InputForm;
import calculator.domain.expression.recognizer.BasicFormRecognizer;
import calculator.domain.expression.recognizer.CustomFormRecognizer;
import calculator.domain.expression.recognizer.InputFormRecognizer;
import java.util.List;
import java.util.Optional;

final class InputFormFactory {
    private final List<InputFormRecognizer> chain =
            List.of(new CustomFormRecognizer(), new BasicFormRecognizer());

    InputForm createFrom(String raw) {
        for (InputFormRecognizer r : chain) {
            Optional<InputForm> hit = r.recognize(raw);
            if (hit.isPresent()) {
                return hit.get();
            }
        }
        throw new IllegalStateException("구분자가 인식되지 않습니다: " + raw);
    }
}