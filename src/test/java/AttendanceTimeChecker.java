public class AttendanceTimeChecker {
    public AttendPolicy attendanceCheck(String time) {
        String[] split = time.split(":");
        int minute = Integer.parseInt(split[1]);

        if (minute > 30) {
            return AttendPolicy.ABSENT;
        }

        if (minute > 5) {
            return AttendPolicy.LATE;
        }

        return AttendPolicy.ATTEND;
    }
}
