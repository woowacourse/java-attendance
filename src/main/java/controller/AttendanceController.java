package controller;

import view.InputView;

import java.util.regex.Pattern;

public class AttendanceController {
    private final Pattern MENU_CHOICES = Pattern.compile("[1234Q]");
    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        String menuSelection = inputView.readMenuSelection();
        validateMenu(menuSelection);
    }

    private void validateMenu(String input) {
        if (!MENU_CHOICES.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 메뉴에 없는 선택지입니다.");
        }
    }
}
