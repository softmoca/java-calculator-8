package calculator.controller;


import calculator.domain.StringCalculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private final StringCalculator calculator;

    public CalculatorController() {
        this.calculator = new StringCalculator();
    }


    public void run() {
        try {
            OutputView.printInputPrompt();
            String input = InputView.readCalculationInput();

            int result = calculator.calculate(input);

            OutputView.printResult(result);

        } catch (IllegalArgumentException e) {
            handleError(e);
        }
    }

    private void handleError(IllegalArgumentException e) {
        OutputView.printError(e.getMessage());
        throw e;
    }
}
