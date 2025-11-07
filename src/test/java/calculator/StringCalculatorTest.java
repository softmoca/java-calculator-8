package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {


    @Test
    void 기본_구분자로_합을_계산한다() {
        StringCalculator calculator = new StringCalculator();

        int result = calculator.calculate("1,2:3");

        assertThat(result).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자로_합을_계산한다() {
        StringCalculator calculator = new StringCalculator();

        int result = calculator.calculate("//;\\n1;2;3");

        assertThat(result).isEqualTo(6);
    }

}
