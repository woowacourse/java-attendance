package view;

public class InputValidator {
    public static void validateMenuInput(String menuInput) {
        if(!menuInput.matches("[1234Qq]")) {
            throw new IllegalArgumentException("존재하는 메뉴 번호를 입력해주세요.");
        }
    }
}
