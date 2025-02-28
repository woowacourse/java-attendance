package view;

import domain.Feature;
import java.util.Scanner;

public class InputView {

    private static final String FEATURE_SELECT_FORMAT = "%s. %s%n";
    private static final String EMPTY_ERROR_MESSAGE = "입력값이 없습니다.";

    public String readFeature() {
        Scanner scanner = new Scanner(System.in);
        for (Feature feature : Feature.values()) {
            System.out.printf(FEATURE_SELECT_FORMAT, feature.number, feature.name);
        }

        String input = scanner.nextLine();
        validateEmptyString(input);
        return input;
    }

    private void validateEmptyString(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_ERROR_MESSAGE);
        }
    }
}