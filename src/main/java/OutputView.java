import java.util.List;

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

    public void printDismissalCrews(List<Crew> dismissalCrewsByImportance) {
        System.out.println(" 제적 위험자 조회 결과");
        dismissalCrewsByImportance.stream().forEach(crew -> System.out.println("- "+crew.getNickname()+": 결석 "+crew.getCrewAbsentCount()+", 지각 "+crew.getCrewLateCount()+"회 ("+crew.getDismissalStatus().getTarget()+")"));
    }
}
