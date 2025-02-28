package domain.menu;

import exception.ErrorException;
import java.util.Arrays;

public enum Menu {

    ATTENDANCE_REGISTER("1", "출석 확인"),
    ATTENDANCE_EDIT("2", "출석 수정"),
    CREW_ATTENDANCE("3", "크루별 출석 기록 확인"),
    EXPULSION_RISK("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String code;
    private final String description;

    Menu(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Menu of(String code) {
        return Arrays.stream(values())
                .filter(menu -> menu.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new ErrorException("존재하지 않는 메뉴입니다."));
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
