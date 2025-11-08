package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

    @Test
    void 음수가_포함되면_예외가_발생한다() {
        String[] tokens = {"-1", "2"};

        assertThatThrownBy(() -> Numbers.from(tokens))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수");
    }


    @Test
    void 빈_문자열이_포함되면_예외가_발생한다() {
        String[] tokens = {"1", "", "2"};

        assertThatThrownBy(() -> Numbers.from(tokens))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("빈 값");
    }

    @Test
    void 양수가아닌_문자열이_포함되면_예외가_발생한다() {
        String[] tokens = {"1", "kk", "2"};

        assertThatThrownBy(() -> Numbers.from(tokens))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자가 아닌 값");
    }

    @Test
    void 같은_값을_가진_Numbers는_동등하다() {
        Numbers numbers1 = Numbers.from(new String[]{"1", "2", "3"});
        Numbers numbers2 = Numbers.from(new String[]{"1", "2", "3"});

        assertThat(numbers1).isEqualTo(numbers2);
    }

    @Test
    void 다른_값을_가진_Numbers는_동등하지_않다() {
        Numbers numbers1 = Numbers.from(new String[]{"1", "2", "3"});
        Numbers numbers2 = Numbers.from(new String[]{"1", "2"});

        assertThat(numbers1).isNotEqualTo(numbers2);
    }


}
