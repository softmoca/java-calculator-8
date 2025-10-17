package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 기본_구분자_사용() {
        assertSimpleTest(() -> {
            run("1,2:3");
            assertThat(output()).contains("결과 : 6");
        });
    }

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
    void 기본_구분자_사용2() {

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


    // 토큰 무결성
    @Test
    void 예외_연속_구분자_중간_빈토큰() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_말단_구분자_말단_빈토큰() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1:"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_전체_구분자만_양쪽_빈토큰() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(","))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    // 값 제약
    @Test
    void 예외_음수_포함() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_제로_포함() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("0,1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_비숫자_포함() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,a"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_선행0_불허() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("01,2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 커스텀 구분자 형식
    @Test
    void 예외_커스텀_구분자_누락() {
        // 문제 명세: "//\n1;2" → 여기서는 "\\n" 형태를 그대로 사용
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n1;2"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_커스텀_구분자_두글자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//12\\n1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_커스텀_구분자_숫자() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//1\\n1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_파싱_범위_초과() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("99999999999999999999,1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_합계_오버플로우() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("9223372036854775807,1"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
