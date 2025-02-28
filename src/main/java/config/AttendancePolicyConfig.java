package config;

import domain.policy.attend.AttendancePolicy;
import domain.policy.attend.date.AttendanceDatePolicy;
import domain.policy.attend.time.AttendanceTimePolicy;

public class AttendancePolicyConfig {

    private AttendancePolicyConfig() {
    }

    private static AttendancePolicy attendancePolicy;

    public static AttendancePolicy getInstance() {
        if (attendancePolicy == null) {
            attendancePolicy = new AttendancePolicy(
                    new AttendanceDatePolicy(),
                    new AttendanceTimePolicy());
        }
        return attendancePolicy;
    }
}
