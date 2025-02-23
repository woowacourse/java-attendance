package dto.requeset;

import java.util.Arrays;

public enum AttendanceBookDecision {
    출석("1"),
    출석_기록_수정("2"),
    출석_기록_확인("3"),
    제적_위험자_확인("4"),
    종료("Q"),
    ;
    
    private final String input;
    
    AttendanceBookDecision(String input) {
        this.input = input;
    }
    
    public static AttendanceBookDecision from(String input) {
        return Arrays.stream(AttendanceBookDecision.values())
                .filter(decision -> decision.input.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 선택입니다. (입력 : %s)".formatted(input)));
    }
}
