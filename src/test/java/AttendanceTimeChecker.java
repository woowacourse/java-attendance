public class AttendanceTimeChecker {
    public AttendPolicy attendanceCheck(String time) {
        String[] split = time.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);

        if (hour < 10) {
            return AttendPolicy.ATTEND;
        }

        if (minute > 30) {
            return AttendPolicy.ABSENT;
        }

        if (minute > 5) {
            return AttendPolicy.LATE;
        }

        return AttendPolicy.ATTEND;
    }
}
