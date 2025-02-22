package attendance.repository;

import static attendance.domain.AttendanceStatus.ABSENT;
import static attendance.domain.AttendanceStatus.ATTEND;
import static attendance.domain.AttendanceStatus.LATE;

import attendance.domain.AcademicStatus;
import attendance.domain.Attendance;
import attendance.domain.Time;
import attendance.dto.CrewNameAndAcademicStatusDTO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceRepository {

    private final List<Attendance> attendances;

    public AttendanceRepository(final List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(final Attendance currentAttendance) {

        for (Attendance attendance : attendances) {
            validateAlreadyHasAttendance(attendance, currentAttendance);
        }
        attendances.add(currentAttendance);
    }

    private void validateAlreadyHasAttendance(final Attendance attendance, final Attendance currentAttendance) {

        if (attendance.isAlreadyAttendance(currentAttendance)) {
            throw new IllegalArgumentException("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
        }
    }

    public List<Attendance> findAllAttendanceByName(final String name) {

        return attendances.stream()
                .filter(attendance -> attendance.getCrewName().equals(name))
                .filter(attendance -> attendance.getAttendanceTime().getMonth() == LocalDate.now().getMonthValue())
                .sorted(Comparator.comparingInt(attendance -> attendance.getAttendanceTime().getDay()))
                .toList();
    }

    public Attendance findAttendanceByNameAndLocalDate(final String name, int year, int month, int day) {

        return attendances.stream()
                .filter(attendance -> attendance.isSameByNameAndLocalDate(name, year, month, day))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 출석 기록입니다."));
    }


    public void initAbsent(final String name) {

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();

        IntStream.range(1, currentDay)
                .mapToObj(day -> LocalDate.of(currentYear, currentMonth, day))
                .filter(date -> !isWeekend(date))
                .filter(date -> isAbsent(name, date))
                .forEach(date -> attendances.add(new Attendance(name, new Time(date, "--", "--", true))));
    }

    private boolean isWeekend(final LocalDate date) {

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isAbsent(final String name, final LocalDate date) {

        try {
            findAttendanceByNameAndLocalDate(name, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    public CrewNameAndAcademicStatusDTO getAcademicStatusByName(final String name) {

        List<Attendance> attendances = findAllAttendanceByName(name);

        Map<String, Long> counts = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getAttendanceStatus, Collectors.counting()));

        int attend = counts.getOrDefault(ATTEND.getValue(), 0L).intValue();
        int late = counts.getOrDefault(LATE.getValue(), 0L).intValue();
        int absent = counts.getOrDefault(ABSENT.getValue(), 0L).intValue();

        return new CrewNameAndAcademicStatusDTO(name, attend, late, absent,
                AcademicStatus.getAcademicStatus(late, absent));
    }
}
