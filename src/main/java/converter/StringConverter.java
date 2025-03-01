package converter;

import domain.Attendance;
import domain.AttendanceTime;
import domain.Crew;
import domain.Crews;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringConverter {

    public Crews convertToCrews(List<String> rawAttendances) {
        Set<Crew> crews = new HashSet<>();
        for (String rawAttendance : rawAttendances) {
            String rawNickname = rawAttendance.split(",")[0];
            validateNullOrBlank(rawNickname);
            crews.add(new Crew(rawNickname));
        }
        return new Crews(crews);
    }

    public String[] splitToNicknameAndTime(String rawAttendanceInfo) {
        return rawAttendanceInfo.split(",");
    }

    public Attendance convertToAttendance(String rawCheckInDateTime, Crew crew) {
        LocalDateTime checkInTime = convertToLocalDateTime(rawCheckInDateTime);
        return Attendance.of(crew, AttendanceTime.of(checkInTime));
    }

    public Attendance convertToAttendance(Crew crew, String rawCheckInTime, LocalDate today) {
        validateNullOrBlank(rawCheckInTime);
        validateTimeFormat(rawCheckInTime);
        LocalDateTime checkInTime = LocalDateTime.of(today, LocalTime.parse(rawCheckInTime));

        return Attendance.of(crew, new AttendanceTime(checkInTime));
    }

    private void validateTimeFormat(String time) {
        String regExpression = "^\\d{2}:\\d{2}$";
        if (!time.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    public LocalDateTime convertToLocalDateTime(String rawDateTime) {
        validateLocalDateTimeFormat(rawDateTime);
        return LocalDateTime.parse(rawDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private void validateLocalDateTimeFormat(String dateTime) {
        validateNullOrBlank(dateTime);
        String regExpression = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        if (!dateTime.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("값이 null 또는 공백입니다.");
        }
    }
}
