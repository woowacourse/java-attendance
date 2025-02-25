package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalTime;

public class CampusTime {

    private static final LocalTime CAMPUS_OPEN = LocalTime.of(8, 0);
    private static final LocalTime CAMPUS_CLOSE = LocalTime.of(23, 0);
    private static final String COLON = ":";

    private final LocalTime time;

    private CampusTime(String timeString) {
        validateSeparatorIsColon(timeString);
        validateTimeFormat(timeString);
        validateCampusTimeRange(timeString);
        int hour = Integer.parseInt(timeString.split(COLON)[0]);
        int minute = Integer.parseInt(timeString.split(COLON)[1]);
        this.time = LocalTime.of(hour, minute);
    }

    public static CampusTime fromHourColonMinute(final String timeString) {
        return new CampusTime(timeString);
    }

    public boolean isAfter(final LocalTime time) {
        return this.time.isAfter(time);
    }

    public boolean isBefore(final LocalTime time) {
        return this.time.isBefore(time);
    }

    private void validateSeparatorIsColon(String time) {
        if (!time.contains(COLON) || time.split(COLON).length != 2) {
            throw CustomException.from(ErrorMessage.SEPARATE_WITH_COLON_ERROR);
        }
    }

    private void validateTimeFormat(String timeString) {
        String hour = timeString.split(COLON)[0];
        String minute = timeString.split(COLON)[1];
        try {
            validateTimeRange(hour, minute);
        } catch (NumberFormatException e) {
            throw CustomException.from(ErrorMessage.TIME_FORMAT_ERROR);
        }
    }

    private void validateTimeRange(String hour, String minute) {
        int parsedHour = Integer.parseInt(hour);
        int parsedMinute = Integer.parseInt(minute);
        if (parsedHour < 0 || parsedHour > 24) {
            throw CustomException.from(ErrorMessage.OUT_OF_HOUR_RANGE);
        }
        if (parsedMinute < 0 || parsedMinute > 60) {
            throw CustomException.from(ErrorMessage.OUT_OF_MINUTE_RANGE);
        }
    }

    private void validateCampusTimeRange(String timeString) {
        int hour = Integer.parseInt(timeString.split(COLON)[0]);
        int minute = Integer.parseInt(timeString.split(COLON)[1]);
        // TODO(Refactor) : LocalTime을 유효성 검증 단계와 생성 단계 두 단계에서 생성하는데, 하나로 통일하기
        LocalTime time = LocalTime.of(hour, minute);
        if (time.isBefore(CAMPUS_OPEN) || time.isAfter(CAMPUS_CLOSE)) {
            throw CustomException.from(ErrorMessage.OUT_OF_CAMPUS_TIME_RANGE);
        }
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMinute() {
        return time.getMinute();
    }

}
