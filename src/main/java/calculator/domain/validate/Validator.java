package calculator.domain.validate;

import java.util.List;

public interface Validator {

    /**
     * 문자열 토큰 리스트에 대해 계약 위반을 검사. - 빈 토큰 금지 - 숫자만 허용 - 0 및 음수 금지 - 선행 0 불허(정책 고정): "01" 등
     * <p>
     * 위반 시 IllegalArgumentException을 던진다.
     */
    void validate(List<String> tokens);
}
