public class Attendance {

    public AttendanceStatus attend(String nickname, String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);

        if (hour == 10 && minute > 5) {
            if (minute > 30) {
            return AttendanceStatus.ABSENCE;
            }
            return AttendanceStatus.LATE;
        }

        if(hour == 13 && minute > 5) {
            if (minute > 30) {
                return AttendanceStatus.ABSENCE;
            }
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTEND;
    }
}
