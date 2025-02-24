package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Attendances {

    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public boolean contains(Attendance attendance) {
        return attendances.stream().anyMatch(attendance::equals);
    }

    public void checkIn(Attendance attendance) {
        validateExistAttendance(attendance);

        attendances.add(attendance);
    }

    public Attendance modify(Crew crew, LocalDateTime modifiedCheckInTime) {
        Optional<Attendance> existAttendance = find(crew, modifiedCheckInTime.toLocalDate());
        existAttendance.ifPresent(attendances::remove);
        Attendance newAttendance = Attendance.of(crew, modifiedCheckInTime);
        checkIn(newAttendance);

        return newAttendance;
    }

    public AttendanceStatistics createStatistics(Crew crew, LocalDate today) {
        Attendances filteredAttendance = findByCrewThisMonth(crew, today);
        return AttendanceStatistics.of(crew, filteredAttendance.createAttendanceTypeCounts());
    }

    private Map<AttendanceType, Integer> createAttendanceTypeCounts() {
        Map<AttendanceType, Integer> attendanceTypesCount = Arrays.stream(AttendanceType.values())
                .collect(Collectors.toMap(Function.identity(), type -> 0));

        for (Attendance attendance : attendances) {
            AttendanceType type = attendance.getAttendanceType();
            attendanceTypesCount.put(type, attendanceTypesCount.get(type) + 1);
        }

        return attendanceTypesCount;
    }

    public Attendances findByCrewThisMonth(Crew crew, LocalDate today) {
        List<Attendance> attendances = new ArrayList<>();
        for (int i = 1; i < today.getDayOfMonth(); i++) {
            LocalDate day = LocalDate.of(today.getYear(), today.getMonth(), i);
            collectAttendanceByCrewAndDay(attendances, crew, day);
        }

        return Attendances.of(attendances);
    }

    private void collectAttendanceByCrewAndDay(List<Attendance> attendances, Crew crew, LocalDate day) {
        if (Holiday.isHolidayOrWeekend(day)) {
            return;
        }

        Optional<Attendance> optionalAttendance = find(crew, day);
        if (optionalAttendance.isPresent()) {
            attendances.add(optionalAttendance.get());
            return;
        }
        attendances.add(Attendance.createTimeNullAbsence(crew, day));
    }

    public Optional<Attendance> find(Crew crew, LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.isSame(crew, date)) {
                return Optional.of(attendance);
            }
        }
        return Optional.empty();
    }

    private void validateExistAttendance(Attendance newAttendance) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDateAndCrew(newAttendance))
                .findAny()
                .ifPresent(error -> {
                    throw new IllegalArgumentException("이미 출석한 경우에는 다시 출석할 수 없습니다.");
                });
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
