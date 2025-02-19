package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Crew {
    private final String nickName;
    private List<Attendance> attendances;

    public Crew(String nickName) {
        this.nickName = nickName;
        this.attendances = new ArrayList<>();
    }

    public CrewDto toDto() {
        return new CrewDto(nickName, calculateLateCount(), calculateAbsentCount(), getPenaltyStatus());
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    private PenaltyStatus getPenaltyStatus() {
        return PenaltyStatus.getInstance(calculateNonAttendanceCount());
    }

    private Integer calculateNonAttendanceCount() {
        return calculateAbsentCount() + calculateLateCount() / 3;
    }

    private Integer calculateLateCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.toDto().getLate().equals(true))
                .count();
    }

    private Integer calculateAbsentCount() {
        return (int) attendances.stream()
                .filter(attendance -> attendance.toDto().getAbsent().equals(true))
                .count();
    }

    public Boolean isEqualTo(String nickname) {
        return this.nickName.equals(nickname);
    }

    public Boolean isAlreadyAttend() {
        LocalDate today = LocalDate.now();
        for (Attendance attendance : attendances) {
            if (attendance.isEqualTo(today)) {
                return true;
            }
        }
        return false;
    }
}
