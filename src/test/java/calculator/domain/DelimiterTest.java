package calculator.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DelimiterTest {

    @Test
    void 기본_구분자로_문자열을_분리한다() {
        // given
        Delimiter delimiter = Delimiter.getDefault();

        // when
        String[] result = delimiter.split("1,2:3");

        // then
        assertThat(result).containsExactly("1", "2", "3");
    }


    @Test
    void 커스텀_구분자로_문자열을_분리한다() {
        // given
        Delimiter delimiter = Delimiter.custom(";");

        // when
        String[] result = delimiter.split("1;2;3");

        // then
        assertThat(result).containsExactly("1", "2", "3");
    }


}
