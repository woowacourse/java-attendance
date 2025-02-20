package controller;

import exception.InvalidMenuException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Menu {
    ATTENDANCE_CHECK("1", "출석 확인"),
    ATTENDANCE_MODIFY("2", "출석 수정"),
    ATTENDANCE_HISTORY_CHECK("3", "크루별 출석 기록 확인"),
    CREW_STATUS_CHECK("4", "제적 위험자 확인"),
    QUIT("Q", "종료")
    ;

    private final String inputValue;
    private final String expression;

    Menu(String inputValue, String expression) {
        this.inputValue = inputValue;
        this.expression = expression;
    }

    public static Menu of(String inputValue) {
        return Arrays.stream(values())
                .filter(menu -> menu.getInputValue().equals(inputValue))
                .findFirst()
                .orElseThrow(InvalidMenuException::new);
    }

//    public static List<Menu> sortByNumber() {
//        return Arrays.stream(Menu.values())
//                .sorted(Comparator.comparing(Menu::))
//                .toList();
//    }

    public String getInputValue() {
        return inputValue;
    }

    public String getExpression() {
        return expression;
    }
}
