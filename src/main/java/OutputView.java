public class OutputView {

    public void printAttendanceResult(String localDateTimeToday, AttendanceStatus attendanceStatus) {
        System.out.println(localDateTimeToday+attendanceStatus.getStatus());
    }

    public void printAttendanceTimeLine(Crew crew) {
        System.out.println(crew.getNickname()+"의 출석 기록입니다.");

        AttendTimes attendTimes = crew.getAttendTimes();

        System.out.println("출석:"+attendTimes.calculateAttendedCount());
        System.out.println("지각:"+attendTimes.calculateLateCount());
        System.out.println("결석:"+attendTimes.calculateAbsentCount());

        System.out.println(DangerousTarget.getWarningStatus(attendTimes.calculateLateCount(),attendTimes.calculateAbsentCount()).getTarget());
    }
}
