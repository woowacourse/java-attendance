package attendance.view;

public class InputValidator {

    public void validateIsNullOrEmpty(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
        }
    }
}
