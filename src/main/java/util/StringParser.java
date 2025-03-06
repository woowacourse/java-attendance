package util;

import domain.AttendanceTime;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParser {

    private static final Pattern FILE_PATTERN =
            Pattern.compile("^([^,]+),\\s*(\\d{4}-\\d{2}-\\d{2})\\s*(\\d{1,2}):(\\d{1,2})$");

    public AttendanceInfo parseToAttendanceInfo(String rawAttendanceInfo) {
        Matcher matcher = FILE_PATTERN.matcher(rawAttendanceInfo);
        validateMatches(matcher);
        String crewName = matcher.group(1);
        LocalDate attendanceDate = LocalDate.parse(matcher.group(2));
        int hour = Integer.parseInt(matcher.group(3));
        int minute = Integer.parseInt(matcher.group(4));

        return new AttendanceInfo(crewName, attendanceDate, new AttendanceTime(hour, minute));
    }

    private void validateMatches(Matcher matcher) {
        if (!matcher.matches()) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 올바르지 않습니다. '이름,YYYY-MM-DD HH:MM' 형식이어야 합니다.");
        }
    }
}
