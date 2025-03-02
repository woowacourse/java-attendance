package io.dto;

import java.util.Arrays;

public enum MenuSelect {
    출석_확인("1"),
    출석_수정("2"),
    크루별_출석_기록_확인("3"),
    제적_위험자_확인("4"),
    종료("Q"),
    ;
    
    private final String expectedInput;
    
    MenuSelect(final String expectedInput) {
        this.expectedInput = expectedInput;
    }
    
    public static MenuSelect of(String input) {
        return Arrays.stream(MenuSelect.values())
                .filter(value -> value.expectedInput.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("1, 2, 3, 4, Q 중에 입력해주세요."));
    }
}
