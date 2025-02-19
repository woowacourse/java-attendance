package attendance.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> attendances;

    public Attendances() {
        this.attendances = new ArrayList<>();
    }

    public void initAttendances(final Crews crews, final List<List<String>> attendanceRecords) {
        for (List<String> attendanceRecord : attendanceRecords) {
            String crewName = attendanceRecord.getFirst();
            Crew crew = crews.findCrew(crewName);

            List<String> dateInfo = List.of(attendanceRecord.getLast().split(" "));
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(dateInfo.getFirst()),
                    LocalTime.parse(dateInfo.getLast()));

            AttendanceType status = AttendanceType.of(localDateTime);

            attendances.add(new Attendance(crew, localDateTime, status));
        }
        fillAbsentDay(crews);
    }

    private void fillAbsentDay(Crews crews) {
        for (Crew crew : crews.getCrews()) {
            LocalDate firstDay = LocalDate.of(2025, 2, 1);
            LocalDate today = LocalDate.now();

            for (LocalDate day = firstDay; day.isBefore(today); day = day.plusDays(1)) {
                if (isWorkDay(day) && !isExistingDay(crew, day)) {
                    // 출근날인데 없네? -> 결석으로 추가
                    LocalDate localDate = LocalDate.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth());
                    LocalTime localTime = LocalTime.of(0, 0);
                    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
                    attendances.add(new Attendance(crew, localDateTime, AttendanceType.ABSENT));
                }
            }
        }
    }

    private boolean isExistingDay(Crew crew, LocalDate day) {
        boolean flag = false;
        for (Attendance attendance : attendances) {
            if (attendance.isSameCrewDate(crew, day)) {
                flag = true;
            }
        }
        return flag;
    }

    private boolean isWorkDay(LocalDate today) {
        return !today.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !today.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public void add(Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findMatchCrewDate(Crew crew, LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewDate(crew, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 해당 크루의 출석 기록이 존재하지 않습니다."));
    }

    public void modifyAttendances(final Crew crew, final LocalDateTime localDateTime) {
        for (Attendance attendance : attendances) {
            modifyAttendance(crew, localDateTime, attendance);
        }
    }

    private static void modifyAttendance(final Crew crew, final LocalDateTime localDateTime,
                                         final Attendance attendance) {
        LocalDate localDate = localDateTime.toLocalDate();
        if (attendance.isSameCrewDate(crew, localDate)) {
            attendance.modifyLocalDateTime(localDateTime);
            attendance.modifyAttendanceType(localDateTime);
        }
    }

    public String findOriginalTime(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getTimeValue();
    }

    public AttendanceType findOriginalType(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getType();
    }

    public List<Attendance> findCrewAttendances(Crew crew) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrew(crew))
                .toList();
    }


}
