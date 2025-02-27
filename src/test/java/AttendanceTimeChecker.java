public class AttendanceTimeChecker {
    public AttendPolicy attendanceCheck(String time) {
        String[] split = time.split(":");
        int minute = Integer.parseInt(split[1]);

        if (minute > 30) {
            return AttendPolicy.ABSENT;
        }
        return AttendPolicy.LATE;
    }
}
