package attendance.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public enum AttendanceStatus {
    ATTEND(0),
    LATE(5),
    ABSENCE(30),
    ;

    private final int deadline;

    AttendanceStatus(int deadline) {
        this.deadline = deadline;
    }

    public static AttendanceStatus from(LocalTime currentTime, EducationSchedule educationSchedule) {
        return Arrays.stream(AttendanceStatus.values())
                .sorted(Comparator.reverseOrder())
                .filter(attendanceState -> isAfterDeadLine(currentTime, educationSchedule, attendanceState))
                .findFirst()
                .orElse(AttendanceStatus.ATTEND);
    }

    private static boolean isAfterDeadLine(
            LocalTime currentTime,
            EducationSchedule educationSchedule,
            AttendanceStatus attendanceStatus
    ) {
        return currentTime.isAfter(educationSchedule.getStartTime().plusMinutes(attendanceStatus.deadline));
    }
}
