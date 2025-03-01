package domain;

import domain.constant.StandardDate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attendances {

    private final Map<String, List<Attendance>> attendances;

    public Attendances(Map<String, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public void addAttendanceLog(String nickname, LocalDateTime localDateTime) {
        validateHoliday(localDateTime.toLocalDate());
        if (!attendances.containsKey(nickname)) {
            List<Attendance> logs = new ArrayList<>();
            logs.add(new Attendance(localDateTime));
            attendances.put(nickname, logs);
            return;
        }

        List<Attendance> logs = attendances.get(nickname);
        logs.add(new Attendance(localDateTime));
    }

    public List<Attendance> getLogsWithName(String nickname) {
        return attendances.get(nickname);
    }

    public Attendance findLogWithNameAndDate(String nickname, LocalDate date) {
        return getLogsWithName(nickname).stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] " + nickname + " 크루의 해당 일자 출석 기록이 없습니다."));

    }

    public int calculateAttendanceCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> AttendanceStatus.judge(attendance.getLocalDateTime()) == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int calculateLateCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> AttendanceStatus.judge(attendance.getLocalDateTime()) == AttendanceStatus.LATENESS)
                .count();
    }

    public int calculateAbsentCount(String nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> AttendanceStatus.judge(attendance.getLocalDateTime()) == AttendanceStatus.ABSENCE)
                .count();
    }

    public void updateAttendance(String nickname, LocalDateTime updateDateTime) {
        validateHoliday(updateDateTime.toLocalDate());
        List<Attendance> attendancesWithCrew = attendances.get(nickname);
        attendancesWithCrew.stream()
                .filter(attendance -> attendance.isEqualTo(updateDateTime.toLocalDate()))
                .findFirst()
                .ifPresent(attendance -> attendance.updateAttendance(updateDateTime.toLocalTime()));
    }

    public List<String> getCrewNames() {
        return attendances.keySet().stream().toList();
    }

    public void recordAllAbsences() {
        attendances.keySet().forEach(this::recordAbsence);
    }

    private void recordAbsence(String nickname) {
        StandardDate.TODAY.withDayOfMonth(1)
                .datesUntil(StandardDate.TODAY)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(date -> !Holiday.check(date))
                .filter(date -> !isAlreadyAttend(nickname, date))
                .forEach(date -> addAttendanceLog(nickname, LocalDateTime.of(date, LocalTime.MAX)));
    }

    private boolean isAlreadyAttend(String nickname, LocalDate date) {
        return attendances.get(nickname).stream().anyMatch(attendance -> attendance.isEqualTo(date));
    }

    private void validateHoliday(LocalDate date) {
        if (Holiday.check(date)) {
            throw new IllegalArgumentException("[ERROR] " + date.getMonthValue() + "월 " + date.getDayOfMonth() + "일은 공휴일입니다.");
        }
    }
}
