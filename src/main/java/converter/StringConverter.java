package converter;

import constant.Command;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;

public class StringConverter {

    public Crews convertToCrews(List<String> rawAttendances) {
        Set<Crew> crews = new HashSet<>();
        for (String rawAttendance : rawAttendances) {
            String rawNickname = rawAttendance.split(",")[0];
            validateNullOrBlank(rawNickname);
            crews.add(Crew.of(rawNickname));
        }
        return Crews.of(crews);
    }

    public Attendances convertToAttendances(List<String> rawAttendances, Crews crews) {
        List<Attendance> attendances = new ArrayList<>();
        for (String rawAttendance : rawAttendances) {
            String[] attendanceInfo = rawAttendance.split(",");

            String rawNickname = attendanceInfo[0];
            String rawCheckInDateTime = attendanceInfo[1];

            Crew crew = convertToCrew(rawNickname, crews);
            attendances.add(Attendance.of(crew, convertToLocalDateTime(rawCheckInDateTime)));
        }

        return Attendances.of(attendances);
    }

    public Crew convertToCrew(String rawNickname, Crews crews) {
        validateNullOrBlank(rawNickname);
        return crews.findByNickname(rawNickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));
    }

    public Attendance convertToAttendance(String rawNickname, String rawCheckInTime, LocalDate today) {
        validateNullOrBlank(rawNickname);
        Crew crew = Crew.of(rawNickname);

        validateNullOrBlank(rawCheckInTime);
        validateTimeFormat(rawCheckInTime);
        LocalDateTime checkInTime = LocalDateTime.of(today, LocalTime.parse(rawCheckInTime));

        return Attendance.of(crew, checkInTime);
    }

    public Command convertToCommand(String rawCommand) {
        return Command.find(rawCommand);
    }

    public Crew convertToNickname(String rawNickname) {
        validateNullOrBlank(rawNickname);

        return Crew.of(rawNickname);
    }

    public LocalDateTime convertToLocalDateTime(String rawDateTime) {
        validateLocalDateTimeFormat(rawDateTime);

        return LocalDateTime.parse(rawDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime convertToLocalDateTime(String rawDay, String rawTime, LocalDate today) {
        validateDayFormat(rawDay);
        validateLastDayOfMonth(Integer.parseInt(rawDay), today);
        LocalDate date = LocalDate.of(today.getYear(), today.getMonthValue(), Integer.parseInt(rawDay));

        validateTimeFormat(rawTime);
        String[] split = rawTime.split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        return LocalDateTime.of(date, time);
    }

    private void validateDayFormat(String rawDay) {
        try {
            Integer.parseInt(rawDay);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("날짜 형식이 아닙니다.");
        }
    }

    private void validateLastDayOfMonth(int day, LocalDate today) {
        int lastDay = today.withDayOfMonth(today.lengthOfMonth()).getDayOfMonth();

        if (day < 1 || day > lastDay) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }

    private void validateLocalDateTimeFormat(String dateTime) {
        validateNullOrBlank(dateTime);
        String regExpression = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        if (!dateTime.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private void validateTimeFormat(String time) {
        String regExpression = "^\\d{2}:\\d{2}$";
        if (!time.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력은 null이거나 공백일 수 없습니다.");
        }
    }
}
