package domain;

import java.util.Arrays;

public enum SelectionOption {
    ADD_ATTENDANCE("1"),
    EDIT_ATTENDANCE("2"),
    GET_ATTENDANCE_HISTORY("3"),
    CHECK_ABSENCE_USERS("4"),
    QUIT("Q");

    private final String option;

    SelectionOption(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }

    public static SelectionOption getSelectOption(String input){
        return Arrays.stream(SelectionOption.values()).filter(select -> select.option.equals(input))
                .findAny().orElseThrow(()->new IllegalArgumentException("[ERROR] 잘못된 입력입니다."));
    }

}
