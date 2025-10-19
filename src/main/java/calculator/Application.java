package calculator;

import calculator.controller.StringAddController;
import calculator.service.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView in = new InputView();
        OutputView out = new OutputView();
        Calculator calculator = new Calculator();
        StringAddController controller = new StringAddController(in, out, calculator);
        controller.handle();
    }
}
