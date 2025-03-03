package constant;

import java.util.Arrays;

public enum Selection {
    ADD("1"),
    EDIT("2"),
    FIND_HISTORY("3"),
    FIND_EXPULSION("4"),
    QUIT("Q");

    private final String value;

    Selection(String value) {
        this.value = value;
    }

    public static Selection findSelection(String selection) {
        return Arrays.stream(Selection.values()).filter(select -> select.value.equals(selection))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 1,2,3,4,Q 중에서 선택하세요!"));
    }
}
