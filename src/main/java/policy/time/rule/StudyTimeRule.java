package policy.time.rule;

import java.time.LocalTime;

public enum StudyTimeRule {

    NORMAL_STUDY_START(10, 0, "일반 교육 시작 시간"),
    NORMAL_STUDY_END(18, 0, "일반 교육 끝 시간"),
    SPECIAL_STUDY_START(13, 0, "특별 교육 시작 시간"),
    SPECIAL_STUDY_END(18, 0, "특별 교육 끝 시간"),
    ;

    private final int hour;
    private final int minute;
    private final String description;

    StudyTimeRule(int hour, int minute, String description) {
        this.hour = hour;
        this.minute = minute;
        this.description = description;
    }

    public LocalTime toLocalTime() {
        return LocalTime.of(hour, minute);
    }

    public String getDescription() {
        return description;
    }
}
