package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Attendances {

    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = new ArrayList<>(attendances);
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(new ArrayList<>(attendances));
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
        return crews.getCrews().stream()
                .collect(Collectors.toMap(
                        crew -> crew,
                        crew -> findByCrewAndMonth(crew, month),
                        (existing, replacement) -> existing
                ));
    }

    public Attendances findByCrewAndMonth(Crew crew, int month) {
        LocalDate today = LocalDate.now();

        List<Attendance> attendances = IntStream.rangeClosed(1, today.getDayOfMonth() - 1)
                .mapToObj(day -> LocalDate.of(today.getYear(), month, day))
                .filter(date -> !Holiday.isHolidayOrWeekend(date))
                .map(date -> find(crew, date)
                        .orElseGet(() -> Attendance.createTimeNullAbsence(crew, date)))
                .toList();

        return Attendances.of(attendances);
    }

    public Optional<Attendance> find(Crew crew, LocalDate localDate) {
        return attendances.stream()
                .filter(attendance -> attendance.isSame(crew, localDate))
                .findFirst()
                .map(attendance -> attendance.clone(attendance));
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
}
