package calculator.view;

public class OutputView {

    public void printStart() {
        System.out.println("덧셈할 문자열을 입력해 주세요.");
    }

    public void printResult(long sum) {
        System.out.println("결과 : " + sum);
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
