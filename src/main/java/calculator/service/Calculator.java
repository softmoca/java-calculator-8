package calculator.service;

import calculator.domain.expression.Expression;
import calculator.domain.tokenizer.Tokenizer;
import calculator.domain.validator.PositiveLongValidator;
import calculator.domain.value.Numbers;
import java.util.List;

public class Calculator {
    public long sumOf(String input) {
        // 1) 표현: 구분자 정책 + 페이로드 분리
        Expression expr = Expression.from(input);

        // 2) 토큰화
        List<String> tokens = Tokenizer.split(expr.payload(), expr.delimiters());

        // 3) 검증
        PositiveLongValidator.validate(tokens);

        // 4) 숫자 변환 + 합산
        return Numbers.from(tokens).sum();
    }
}