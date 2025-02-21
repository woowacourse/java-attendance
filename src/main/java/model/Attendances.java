package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
        if (existAttendance.isPresent()) {
            existAttendance.get().modify(modifiedCheckInTime.toLocalTime());
            return existAttendance.get();
        }
        Attendance attendance = Attendance.of(crew, modifiedCheckInTime);
        checkIn(attendance);

        return attendance;
    }

    public Map<Crew, Attendances> findAll(Crews crews, int month) {
        Map<Crew, Attendances> crewsAttendances = new HashMap<>();
        for (Crew crew : crews.getCrews()) {
            Attendances attendances = findByCrewThisMonth(crew, LocalDate.now());
            crewsAttendances.put(crew, attendances);
        }
        return crewsAttendances;
    }

    public Attendances findByCrewThisMonth(Crew crew, LocalDate today) {
        List<Attendance> attendances = new ArrayList<>();
        for (int i = 1; i < today.getDayOfMonth(); i++) {
            LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), i);
            if (Holiday.isHolidayOrWeekend(date)) {
                continue;
            }

            Optional<Attendance> optionalAttendance = find(crew, date);
            if (optionalAttendance.isEmpty()) {
                attendances.add(Attendance.createTimeNullAbsence(crew, date));
                continue;
            }
            attendances.add(optionalAttendance.get());
        }

        return Attendances.of(attendances);
    }

    public Optional<Attendance> find(Crew crew, LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.isSame(crew, date)) {
                Attendance copy = attendance.clone(attendance);
                return Optional.of(copy);
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
