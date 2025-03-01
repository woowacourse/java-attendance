package view;

import java.util.Arrays;

public enum FeatureType {
    CHECK_ATTENDANCE("1", "출석 확인"),
    EDIT_ATTENDANCE("2", "출석 수정"),
    CHECK_ATTENDANCE_OF_CREW("3", "크루별 출석 기록 확인"),
    CHECK_CREW_OF_BAN_RISK("4", "제적 위험자 확인"),
    QUIT("Q", "종료"),
    ;

    private final String key;
    private final String name;

    FeatureType(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static FeatureType from(String key) {
        return Arrays.stream(FeatureType.values())
                .filter(value -> value.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 입력입니다. 기능 목록에 있는 키만 입력해주세요."));
    }
}
