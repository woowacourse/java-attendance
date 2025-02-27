package domain;

import domain.policy.AbsentPolicy;
import domain.policy.AttendanceState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class AttendanceSheet {

    public static final int ATTENDANCE_YEAR = 2024;
    public static final int ATTENDANCE_MONTH = 12;

    private final AbsentPolicy absentPolicy;
    private final List<Attendance> attendances;

    public AttendanceSheet(AbsentPolicy absentPolicy, List<Attendance> attendances) {
        this.absentPolicy = absentPolicy;
        this.attendances = attendances;
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        validateIsAlreadyAttendance(nickname, date);
        this.attendances.add(new Attendance(nickname, date, time, absentPolicy.checkAttendanceStatus(LocalDateTime.of(date, time))));
    }

    public void validateIsAlreadyAttendance(String nickname, LocalDate date) {
        if (isAttendanceExist(nickname, date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용하세요");
        }
    }

    private boolean isAttendanceExist(String nickname, LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAttendanceExist(nickname, date));
    }

    public void update(String nickname, int dayOfMonth, LocalTime updateTime) {
        LocalDate updateDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, dayOfMonth);

        attendances.stream()
                .filter(attendance -> isAttendanceExist(nickname, updateDate))
                .findFirst()
                .ifPresentOrElse(attendance -> attendance.update(updateTime),
                        () -> {
                            throw new IllegalArgumentException("[ERROR] 출석 기록이 없습니다. 출석 확인 기능을 이용하세요");
                        });
    }

    public Map<AttendanceState, Long> countAttendanceState(String nickname, LocalDate today) {
        Map<AttendanceState, Long> counts = attendances.stream()
                .filter(attendance -> attendance.isSameNickname(nickname))
                .collect(groupingBy(Attendance::getState, counting()));

        initUndefinedState(counts);
        Long absentCount = calculateAbsentCount(today, counts);
        counts.put(AttendanceState.ABSENT, counts.get(AttendanceState.ABSENT)*2 + absentCount);

        return counts;
    }

    private Long calculateAbsentCount(LocalDate today, Map<AttendanceState, Long> counts) {
        return counts.values().stream()
                .reduce(dayCount(today), (allDay, attendedDay) -> allDay - attendedDay);
    }

    private static void initUndefinedState(Map<AttendanceState, Long> counts) {
        Arrays.stream(AttendanceState.values())
                .forEach(state -> counts.putIfAbsent(state, 0L));
    }

    private Long dayCount(LocalDate today) {
        return today.withDayOfMonth(1)
                .datesUntil(today)
                .filter(date -> absentPolicy.isWeekday(date.getDayOfWeek()))
                .filter(absentPolicy::isNotHoliday)
                .count();
    }

    public Map<String, Map<AttendanceState, Long>> countAttendancesState(LocalDate today) {
        return attendances.stream()
                .collect(groupingBy(Attendance::getNickname))
                .keySet().stream()
                .collect(toMap(
                        nickname -> nickname,
                        nicknameForCount -> countAttendanceState(nicknameForCount, today)
                ));
    }

}
