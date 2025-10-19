package calculator.controller;


import calculator.service.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class StringAddController {

    private final InputView in;
    private final OutputView out;
    private final Calculator calculator;

    public StringAddController(InputView in, OutputView out, Calculator calculator) {
        this.in = in;
        this.out = out;
        this.calculator = calculator;
    }

    public void handle() {
        out.printStart();
        String input = in.readLine();
        try {
            long sum = calculator.sumOf(input);
            out.printResult(sum);
        } catch (IllegalArgumentException e) {
            out.printError(e.getMessage());
            throw e;
        }
    }
}
