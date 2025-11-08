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

    @Test
    void 같은_패턴의_Delimiter는_동등하다() {
        Delimiter delimiter1 = Delimiter.custom(";");
        Delimiter delimiter2 = Delimiter.custom(";");

        assertThat(delimiter1).isEqualTo(delimiter2);
    }

    @Test
    void 기본_구분자는_싱글톤이다() {
        Delimiter delimiter1 = Delimiter.getDefault();
        Delimiter delimiter2 = Delimiter.getDefault();

        // 같은 인스턴스
        assertThat(delimiter1).isSameAs(delimiter2);
        // 당연히 equals도 true
        assertThat(delimiter1).isEqualTo(delimiter2);
    }

    @Test
    void 다른_패턴의_Delimiter는_동등하지_않다() {
        Delimiter delimiter1 = Delimiter.custom(";");
        Delimiter delimiter2 = Delimiter.custom(",");

        assertThat(delimiter1).isNotEqualTo(delimiter2);
    }


}
