package controller;

import controller.exception.ServiceNotExistException;
import java.util.Arrays;

public enum Service {
    REGISTER("1", "출석 확인"),
    MODIFY("2", "출석 수정"),
    HISTORY("3", "크루별 출석 기록 확인"),
    PENALTY("4", "제적 위험자 확인"),
    QUIT("Q", "종료"),
    ;

    private final String value;
    private final String meaning;

    Service(String value, String meaning) {
        this.value = value;
        this.meaning = meaning;
    }

    public static Service findByValue(String targetValue) {
        return Arrays.stream(Service.values())
                .filter(service -> service.value.equals(targetValue))
                .findAny()
                .orElseThrow(ServiceNotExistException::new);
    }
}
