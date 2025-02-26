public class OutputView {

    public void printAttendanceResult(String localDateTimeToday, AttendanceStatus attendanceStatus) {
        System.out.println(localDateTimeToday+attendanceStatus.getStatus());
    }
}
