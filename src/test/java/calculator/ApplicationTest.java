package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    @Test
    void 기본_구분자_사용() {

        assertSimpleTest(() -> {
            run("1,2");
            assertThat(output()).contains("결과 : 3");

            run("1,2,3");
            assertThat(output()).contains("결과 : 6");

            run("1,2:3");
            assertThat(output()).contains("결과 : 6");

            run("1,202:3");
            assertThat(output()).contains("결과 : 206");
        });

    }

    @Test
    void 커스텀_구분자_사용2() {
        assertSimpleTest(() -> {
            run("//;\\n1;2;3");
            assertThat(output()).contains("결과 : 6");

            run("//.\\n21.6.7");
            assertThat(output()).contains("결과 : 34");

            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }


    @Test
    void 빈_문자열일_경우() {
        assertSimpleTest(() -> {
            run("\n");
            assertThat(output()).contains("결과 : 0");
        });
    }


    @Test
    void 구분자_없이_줄바꿈() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 커스텀 구분자 형식
    @Test
    void 예외_커스텀_구분자_두글자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//12\\n1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_구문_오류() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;1;2;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
