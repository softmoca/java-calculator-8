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
        Numbers numbers = expression.toNumbers();

        // then
        assertThat(numbers).isEqualTo(Numbers.from(new String[]{"1", "2", "3"}));
    }

    @Test
    void 커스텀_구분자_형식을_파싱한다() {
        // given
        String input = "//;\\n1;2;3";

        // when
        Expression expression = Expression.from(input);
        Numbers numbers = expression.toNumbers();

        // then
        assertThat(numbers).isEqualTo(Numbers.from(new String[]{"1", "2", "3"}));
    }


}
