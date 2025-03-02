package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrewAttendance {
    private final List<Attendance> attendances;

    public CrewAttendance() {
        this.attendances = new ArrayList<>();
    }

    public void add(final LocalDateTime attendance) {
        validateDuplicateDate(attendance);
        attendances.add(new Attendance(attendance));
    }

    private void validateDuplicateDate(final LocalDateTime attendance) {
        if (isExistDay(attendance)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public boolean isExistDay(final LocalDateTime targetDateTime) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isSameDay(new Attendance(targetDateTime)));
    }

    public void modify(final LocalDateTime newAttendance) {
        Attendance prevAttendance = getAttendanceOn(LocalDate.from(newAttendance));
        attendances.remove(prevAttendance);
        attendances.add(new Attendance(newAttendance));
    }

    public Attendance getAttendanceOn(final LocalDateTime targetDay) {
        return getAttendanceOn(LocalDate.from(targetDay));
    }

    public Attendance getAttendanceOn(final LocalDate targetDay) {
        return attendances.stream()
                .filter(attendance -> attendance.record().date().equals(targetDay))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 기록이 존재하지 않습니다."));
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
