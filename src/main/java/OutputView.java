public class OutputView {

    public void printAttendanceResult(String localDateTimeToday, AttendanceStatus attendanceStatus) {
        System.out.println(localDateTimeToday+attendanceStatus.getStatus());
    }

    public void printAttendanceTimeLine(Crew crew) {
        System.out.println(crew.getNickname()+"의 출석 기록입니다.");


        System.out.println("출석:" + crew.getCrewAttendedCount());
        System.out.println("지각:" + crew.getCrewLateCount());
        System.out.println("결석:" + crew.getCrewAbsentCount());

        System.out.println(DangerousTarget.getWarningStatus(crew.getCrewLateCount(), crew.getCrewAbsentCount()).getTarget());
    }
}
