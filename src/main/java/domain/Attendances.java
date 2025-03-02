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

    private final Map<Nickname, List<Attendance>> attendances;

    public Attendances(Map<Nickname, List<Attendance>> attendances) {
        this.attendances = attendances;
    }

    public void addAttendanceLog(Nickname nickname, LocalDateTime localDateTime) {
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

    public List<Attendance> getLogsWithName(Nickname nickname) {
        return attendances.get(nickname);
    }

    public Attendance findLogWithNameAndDate(Nickname nickname, LocalDate date) {
        return getLogsWithName(nickname).stream()
                .filter(attendance -> attendance.isEqualTo(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] " + nickname + " 크루의 해당 일자 출석 기록이 없습니다."));

    }

    public int calculateAttendanceCount(Nickname nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> StandardTime.judge(attendance.getLocalDateTime()) == AttendanceStatus.ATTENDANCE)
                .count();
    }

    public int calculateLateCount(Nickname nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> StandardTime.judge(attendance.getLocalDateTime()) == AttendanceStatus.LATENESS)
                .count();
    }

    public int calculateAbsentCount(Nickname nickname) {
        List<Attendance> logs = getLogsWithName(nickname);
        return (int) logs.stream()
                .filter(attendance -> StandardTime.judge(attendance.getLocalDateTime()) == AttendanceStatus.ABSENCE)
                .count();
    }

    public void updateAttendance(Nickname nickname, LocalDateTime updateDateTime) {
        validateHoliday(updateDateTime.toLocalDate());
        List<Attendance> attendancesWithCrew = attendances.get(nickname);
        attendancesWithCrew.stream()
                .filter(attendance -> attendance.isEqualTo(updateDateTime.toLocalDate()))
                .findFirst()
                .ifPresent(attendance -> attendance.updateAttendance(updateDateTime.toLocalTime()));
    }

    public List<Nickname> getCrewNames() {
        return attendances.keySet().stream().toList();
    }

    public void recordAllAbsences() {
        attendances.keySet().forEach(this::recordAbsence);
    }

    private void recordAbsence(Nickname nickname) {
        StandardDate.TODAY.withDayOfMonth(1)
                .datesUntil(StandardDate.TODAY)
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(date -> !Holiday.check(date))
                .filter(date -> !isAlreadyAttend(nickname, date))
                .forEach(date -> addAttendanceLog(nickname, LocalDateTime.of(date, LocalTime.MAX)));
    }

    private boolean isAlreadyAttend(Nickname nickname, LocalDate date) {
        return attendances.get(nickname).stream().anyMatch(attendance -> attendance.isEqualTo(date));
    }

    private void validateHoliday(LocalDate date) {
        if (Holiday.check(date)) {
            throw new IllegalArgumentException("[ERROR] " + date.getMonthValue() + "월 " + date.getDayOfMonth() + "일은 공휴일입니다.");
        }
    }

    public Nickname checkCrewName(Nickname nickname) {
        if (!attendances.containsKey(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
        return nickname;
    }
}
