package calculator.domain.expression.recognizer;

import calculator.domain.Delimiters;
import calculator.domain.expression.form.CustomForm;
import calculator.domain.expression.form.InputForm;
import java.util.Optional;

public class CustomFormRecognizer implements InputFormRecognizer {
    private static final String PREFIX = "//";
    private static final String SEP = "\\n";

    @Override
    public Optional<InputForm> recognize(String raw) {
        if (raw == null || !raw.startsWith(PREFIX)) {
            return Optional.empty();
        }

        int nl = raw.indexOf(SEP);
        if (nl < 0) {
            throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다: //X\\n...");
        }

        String custom = raw.substring(PREFIX.length(), nl);
        if (custom.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 단일 문자여야 합니다: " + custom);
        }

        char c = custom.charAt(0);
        if (Character.isDigit(c)) {
            throw new IllegalArgumentException("커스텀 구분자가 숫자일 수 없습니다: " + c);
        }

        Delimiters delimiter = Delimiters.from(c);
        String body = raw.substring(nl + SEP.length());
        return Optional.of(new CustomForm(delimiter, body));
    }
}