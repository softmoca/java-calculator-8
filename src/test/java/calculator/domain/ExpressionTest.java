package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void 기본_구분자_형식을_파싱한다() {
        // given
        String input = "1,2:3";

        // when
        Expression expression = Expression.from(input);

        // then
        assertThat(expression.getNumbersText())
                .isEqualTo("1,2:3");
    }

    @Test
    void 커스텀_구분자_형식을_파싱한다() {
        // given
        String input = "//;\\n1;2;3";

        // when
        Expression expression = Expression.from(input);

        // then
        assertThat(expression.getNumbersText())
                .isEqualTo("1;2;3");

    }

    @Test
    void 기본_구분자로_Numbers를_생성한다() {

        // given
        String input = "1,2:3";
        Expression expression = Expression.from(input);

        // when
        Numbers numbers = expression.toNumbers();

        // then
        assertThat(numbers.sum()).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자로_Numbers를_생성한다() {

        // given
        String input = "//;\\n1;2;3";
        Expression expression = Expression.from(input);

        // when
        Numbers numbers = expression.toNumbers();

        // then
        assertThat(numbers.sum()).isEqualTo(6);
    }


}
