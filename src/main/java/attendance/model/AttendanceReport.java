package attendance.model;

import attendance.util.DateUtil;
import java.time.LocalDate;

public class AttendanceReport {
    private final CustomClock clock;
    private final AttendanceHistory attendanceHistory;
    private final LocalDate trainingStartDate;

    public AttendanceReport(CustomClock clock, AttendanceHistory attendanceHistory, LocalDate trainingStartDate) {
        this.clock = clock;
        this.attendanceHistory = attendanceHistory;
        this.trainingStartDate = trainingStartDate;
    }

    public long calculateAttendanceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.PRESENT))
                .count();
    }

    public long calculateLateCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.LATE))
                .count();
    }

    public long calculateAbsenceCount() {
        long count = attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.ABSENT))
                .count();

        return count + calculateNoAttendanceCount();
    }

    public AttendanceWarning calculateWarning() {
        long totalAbsenceCount = calculateAbsenceCount() + calculateLateCount() / AttendanceWarning.LATES_PER_ABSENCE;
        return AttendanceWarning.from(totalAbsenceCount);
    }

    private long calculateNoAttendanceCount() {
        LocalDate endDate = clock.nowDate();

        long absenceCount = 0;
        for (LocalDate date = trainingStartDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (!isRequiredAttendanceDay(date)) {
                continue;
            }
            if (!attendanceHistory.containsDate(date)) {
                absenceCount++;
            }
        }
        return absenceCount;
    }

    private boolean isRequiredAttendanceDay(LocalDate localDate) {
        return !DateUtil.isWeekendOrHoliday(localDate, clock);
    }
}
