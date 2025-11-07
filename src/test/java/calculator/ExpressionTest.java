package calculator;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void 기본_구분자_형식을_파싱한다() {
        // given
        String input = "1,2:3";

        // when
        Expression expression = Expression.from(input);

        // then
        assertThat(expression.getDelimiter())
                .isEqualTo(Delimiter.getDefault());

        assertThat(expression.getNumbersText())
                .isEqualTo("1,2:3");
    }


}
