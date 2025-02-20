package attendance.repository;

import attendance.domain.Attendance;
import attendance.domain.Time;
import attendance.dto.AttendanceCountAndAcademicStatusDTO;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance currentAttendance) {
        for (Attendance attendance : attendances) {
            if (attendance.isAlreadyAttendance(currentAttendance)) {
                throw new IllegalArgumentException("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
            }
        }
        attendances.add(currentAttendance);
    }

    public List<Attendance> findAllAttendanceByName(String name) {
        return attendances.stream()
                .filter(attendance -> attendance.getCrewName().equals(name))
                .filter(attendance -> attendance.getAttendanceTime().getMonth() == LocalDate.now().getMonthValue())
                .sorted(Comparator.comparingInt(attendance -> attendance.getAttendanceTime().getDay()))
                .toList();
    }

    public Attendance findAttendanceByNameAndLocalDate(String name, int year, int month, int day) {

        for (Attendance attendance : attendances) {
            if (attendance.isSameByNameAndLocalDate(name, year, month, day)) {
                return attendance;
            }
        }

        throw new IllegalArgumentException("[ERROR] 존재하지 않는 출석 기록입니다.");
    }

    public void initAbsent(String name) {

        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        for (int day = 1; day < LocalDate.now().getDayOfMonth(); day++) {
            try {
                String dayOfWeek = LocalDate.of(currentYear, currentMonth, day).getDayOfWeek().name();
                if (dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY")) {
                    continue;
                }

                Attendance attendance = findAttendanceByNameAndLocalDate(name, currentYear, currentMonth, day);
            } catch (IllegalArgumentException e) {
                int year = LocalDate.now().getYear();
                int month = currentMonth;
                attendances.add(new Attendance(name, new Time(LocalDate.of(year, month, day), "--", "--", true)));

            }
        }
    }

    public AttendanceCountAndAcademicStatusDTO getAcademicStatusByName(String name) {
        List<Attendance> attendances = findAllAttendanceByName(name);

        Map<String, Long> counts = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getAttendanceStatus, Collectors.counting()));

        int attend = counts.getOrDefault("출석", 0L).intValue();
        int late = counts.getOrDefault("지각", 0L).intValue();
        int absent = counts.getOrDefault("결석", 0L).intValue();

        return new AttendanceCountAndAcademicStatusDTO(attend, late, absent, getAcademicStatus(late, absent));
    }

    private String getAcademicStatus(int late, int absent) {
        return Stream.of(late / 3 + absent)
                .map(count -> {
                    if (count > 5) {
                        return "제적";
                    }
                    if (count >= 3) {
                        return "면담";
                    }
                    if (count == 2) {
                        return "경고";
                    }
                    return "X";
                })
                .findFirst()
                .orElse("X");
    }
}
