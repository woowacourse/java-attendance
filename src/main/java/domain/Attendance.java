package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendance {

    private final Crew crew;
    private final List<LocalDateTime> attendanceTime;

    public Attendance(final Crew crew, final List<LocalDateTime> attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    public void add(final LocalDateTime time) {
        attendanceTime.add(time);
    }

    public LocalDateTime getAttendanceBy(final LocalDate date) {
        return attendanceTime.stream()
                .filter(attendance -> attendance.getDayOfMonth() == date.getDayOfMonth())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(("해당 날자(일)에 해당하는 출석 기록이 없습니다.")));
    }

    public void update(final LocalDateTime updateDateTime) {
        for (int dateIndex = 0; dateIndex < attendanceTime.size(); dateIndex++) {
            LocalDateTime localDateTime = attendanceTime.get(dateIndex);
            if (isEqualAttendance(updateDateTime, localDateTime)) {
                attendanceTime.set(dateIndex, updateDateTime);
            }
        }
    }

    private boolean isEqualAttendance(final LocalDateTime updateDateTime, final LocalDateTime localDateTime) {
        return localDateTime.getDayOfMonth() == updateDateTime.getDayOfMonth();
    }

    public boolean isSame(final String name) {
        return this.crew.isSame(name);
    }

    public boolean isContains(final LocalDate date) {
        return attendanceTime.stream()
                .anyMatch(attendance -> attendance.toLocalDate().equals(date));
    }

    public List<LocalDateTime> getAttendanceTime() {
        return new ArrayList<>(attendanceTime);
    }

    public Crew getCrew() {
        return crew;
    }
}

