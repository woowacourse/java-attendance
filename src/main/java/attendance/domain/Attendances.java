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
        attendances.clear();
        for (List<String> attendanceRecord : attendanceRecords) {
            String crewName = attendanceRecord.getFirst();
            Crew crew = crews.findCrew(crewName);

            List<String> dateInfo = List.of(attendanceRecord.getLast().split(" "));
            LocalDateTime localDateTime = LocalDateTime.of(
                    LocalDate.parse(dateInfo.getFirst()),
                    LocalTime.parse(dateInfo.getLast())
            );

            AttendanceType status = AttendanceType.of(localDateTime);

            attendances.add(new Attendance(crew, localDateTime, status));
        }
        fillAbsentDay(crews);
    }

    private void fillAbsentDay(final Crews crews) {
        LocalDate firstDay = LocalDate.of(2025, 2, 1);
        LocalDate today = LocalDate.now();
        for (Crew crew : crews.getCrews()) {
            checkDaysBeforeToday(crew, firstDay, today);
        }
    }

    private void checkDaysBeforeToday(final Crew crew, final LocalDate firstDay, final LocalDate today) {
        for (LocalDate day = firstDay; day.isBefore(today); day = day.plusDays(1)) {
            checkNotExistingWorkingDay(crew, day);
        }
    }

    private void checkNotExistingWorkingDay(final Crew crew, final LocalDate day) {
        if (isWorkDay(day) && !isExistingDay(crew, day)) {
            LocalTime localTime = LocalTime.of(0, 0);
            LocalDateTime localDateTime = LocalDateTime.of(day, localTime);
            attendances.add(new Attendance(crew, localDateTime, AttendanceType.ABSENT));
        }
    }

    private boolean isExistingDay(final Crew crew, final LocalDate day) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameCrewDate(crew, day));
    }

    private boolean isWorkDay(final LocalDate today) {
        return !today.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !today.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public void add(final Attendance attendance) {
        attendances.add(attendance);
    }

    public Attendance findMatchCrewDate(final Crew crew, final LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrewDate(crew, localDate))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 해당 크루의 출석 기록이 존재하지 않습니다."));
    }

    public void modifyAttendances(final Crew crew, final LocalDateTime modifyDateTime) {
        attendances.stream()
                .filter(attendance -> attendance.isSameCrewDate(crew, modifyDateTime.toLocalDate()))
                .findFirst()
                .ifPresent(attendance -> attendance.modifyLocalDateTime(modifyDateTime));
    }

    public String findOriginalTime(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getTimeValue();
    }

    public AttendanceType findOriginalType(final Crew crew, final LocalDate localDate) {
        return findMatchCrewDate(crew, localDate).getType();
    }

    public List<Attendance> findCrewAttendances(final Crew crew) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameCrew(crew))
                .toList();
    }

    public boolean hasTodayAttendance(Crew crew) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameCrewDate(crew, localDateTime.toLocalDate()));
    }

}
