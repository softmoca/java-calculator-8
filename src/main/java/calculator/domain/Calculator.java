package calculator.domain;

public class Calculator {

    public long sumOf(String input) {
        // UC-03: 빈 입력(null 또는 "")은 0 반환
        if (input == null || input.isEmpty()) {
            return 0L;
        }

        return -1;
    }
}
