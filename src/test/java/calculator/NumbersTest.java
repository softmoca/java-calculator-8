package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class NumbersTest {

    @Test
    void 숫자들의_합을_계산한다() {

        // given
        String[] tokens = {"1", "2"};

        // when
        Numbers numbers = Numbers.from(tokens);
        int sum = numbers.sum();

        // then
        assertThat(sum).isEqualTo(3);
    }

    @Test
    void 다른_숫자들의_합을_계산한다() {
        String[] tokens = {"1", "2", "3"};

        Numbers numbers = Numbers.from(tokens);

        assertThat(numbers.sum()).isEqualTo(6);
    }


}
