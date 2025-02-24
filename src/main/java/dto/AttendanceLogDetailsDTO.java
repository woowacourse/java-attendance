package dto;

import domain.PenaltyStatus;

import java.time.LocalDateTime;
import java.util.List;

public class AttendanceLogDetailsDTO {
    private String name;
    private List<LocalDateTime> attendanceTimes;
    private List<Integer> attendanceDays;
    private int presenceCount;
    private int lateCount;
    private int absenceCount;
    private PenaltyStatus penaltyStatus;

    public AttendanceLogDetailsDTO(String name, List<LocalDateTime> attendanceTimes, List<Integer> attendanceDays, int presenceCount, int lateCount, int absenceCount, PenaltyStatus penaltyStatus) {
        this.name = name;
        this.attendanceTimes = attendanceTimes;
        this.attendanceDays = attendanceDays;
        this.presenceCount = presenceCount;
        this.lateCount = lateCount;
        this.absenceCount = absenceCount;
        this.penaltyStatus = penaltyStatus;
    }

    public String getName() {
        return name;
    }

    public List<LocalDateTime> getAttendanceTimes() {
        return attendanceTimes;
    }

    public List<Integer> getAttendanceDays() {
        return attendanceDays;
    }

    public int getPresenceCount() {
        return presenceCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public PenaltyStatus getPenaltyStatus() {
        return penaltyStatus;
    }
}
