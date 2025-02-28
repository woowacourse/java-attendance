package controller;

import controller.exception.ServiceNotExistException;
import java.util.Arrays;

public enum ServiceChoice {
    REGISTER("1", "출석 확인"),
    MODIFY("2", "출석 수정"),
    HISTORY("3", "크루별 출석 기록 확인"),
    PENALTY("4", "제적 위험자 확인"),
    QUIT("Q", "종료"),
    ;

    private final String value;
    private final String meaning;

    ServiceChoice(String value, String meaning) {
        this.value = value;
        this.meaning = meaning;
    }

    public static ServiceChoice findByValue(String targetValue) {
        return Arrays.stream(ServiceChoice.values())
                .filter(serviceChoice -> serviceChoice.value.equals(targetValue))
                .findAny()
                .orElseThrow(ServiceNotExistException::new);
    }
}
