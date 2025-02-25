package domain;

import java.time.LocalDate;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Attendance findAttendanceByDate(LocalDate date) {
        return this.attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜에 대한 출석 기록이 존재하지 않습니다."));
    }

    public CrewStatus getCrewStatue() {
        return CrewStatus.checkCrewStatus(this.countLate(), this.countUnattended());
    }

    private int countLate() {
        return (int) attendances.stream()
                .filter(Attendance::isLate)
                .count();
    }

    private int countUnattended() {
        return (int) attendances.stream()
                .filter(Attendance::isUnattendedOrNoShow)
                .count();
    }
}
