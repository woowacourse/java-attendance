package attendance.view;

import attendance.domain.SanctionLevel;

public enum SanctionLevelText {
    DISMISS("제적"),
    NEED_MEETING("면담"),
    WARNING("경고"),
    NONE("");

    private final String text;

    SanctionLevelText(String text) {
        this.text = text;
    }

    public static String convert(SanctionLevel level) {
        if (level.equals(SanctionLevel.DISMISS)) {
            return DISMISS.text;
        }
        if (level.equals(SanctionLevel.NEED_MEETING)) {
            return NEED_MEETING.text;
        }
        if (level.equals(SanctionLevel.WARNING)) {
            return WARNING.text;
        }
        return NONE.text;
    }

}
