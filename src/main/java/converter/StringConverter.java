package converter;

import constant.Command;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;

public class StringConverter {

    public Crews convertToCrews(List<String> rawAttendances) {
        List<Crew> crews = rawAttendances.stream()
                .map(rawAttendance -> rawAttendance.split(",")[0])
                .peek(this::validateNullOrBlank)
                .map(Crew::of)
                .toList();

        return Crews.of(crews);
    }

    public Attendances convertToAttendances(List<String> rawAttendances, Crews crews) {
        List<Attendance> attendances = rawAttendances.stream()
                .map(rawAttendance -> rawAttendance.split(","))
                .peek(attendanceInfo -> validateNullOrBlank(attendanceInfo[0]))
                .peek(attendanceInfo -> validateNullOrBlank(attendanceInfo[1]))
                .peek(attendanceInfo -> validateLocalDateTimeFormat(attendanceInfo[1]))
                .map(attendanceInfo -> {
                    Optional<Crew> byNickname = crews.findByNickname(attendanceInfo[0]);
                    if (byNickname.isEmpty()) {
                        System.out.println("hello");
                    }
                    Crew crew = crews.findByNickname(attendanceInfo[0])
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));
                    LocalDateTime checkInTime = convertToLocalDateTime(attendanceInfo[1]);

                    return Attendance.of(crew, checkInTime);
                })
                .toList();

        return Attendances.of(attendances);
    }

    public Attendance convertToAttendance(Crews crews, String rawNickname, String rawCheckInTime) {
        validateExistCrew(crews, rawNickname);
        validateNullOrBlank(rawNickname);
        Crew crew = Crew.of(rawNickname);

        validateNullOrBlank(rawCheckInTime);
        validateTimeFormat(rawCheckInTime);
        LocalDateTime checkInTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(rawCheckInTime));

        return Attendance.of(crew, checkInTime);
    }

    public Command convertToCommand(String rawCommand) {
        return Command.find(rawCommand);
    }

    public Crew convertToNickname(String rawNickname) {
        validateNullOrBlank(rawNickname);

        return Crew.of(rawNickname);
    }

    public LocalDateTime convertToLocalDateTime(String rawDay, String rawTime) {
        validateDayFormat(rawDay);
        LocalDate now = LocalDate.now();
        LocalDate nowDate = LocalDate.of(now.getYear(), now.getMonthValue(), Integer.parseInt(rawDay));

        validateTimeFormat(rawTime);
        String[] split = rawTime.split(":");
        LocalTime nowTime = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));

        return LocalDateTime.of(nowDate, nowTime);
    }

    private void validateDayFormat(String rawDay) {
        int day;
        try {
            day = Integer.parseInt(rawDay);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("날짜 형식이 아닙니다.");
        }

        LocalDate now = LocalDate.now();
        int lastDay = now.withDayOfMonth(now.lengthOfMonth()).getDayOfMonth();

        if (day < 1 || day > lastDay) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }

    private LocalDateTime convertToLocalDateTime(String rawCheckInDateTime) {
        String[] dateTimeParts = rawCheckInDateTime.split(" ");
        return LocalDateTime.of(LocalDate.parse(dateTimeParts[0]),
                LocalTime.parse(dateTimeParts[1] + ":00"));
    }

    private void validateExistCrew(Crews crews, String rawNickname) {
        crews.findByNickname(rawNickname)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 크루입니다."));
    }

    private void validateLocalDateTimeFormat(String dateTime) {
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
