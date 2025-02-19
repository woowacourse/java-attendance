package attendance.view;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import java.time.format.DateTimeFormatter;

public class OutputView {

    private final DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
    private final DateTimeFormatter absenceFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");

    public void printAttendanceHistory(Crew crew) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine(String.format("이번 달 %s의 출석 기록입니다.", crew.getName()));

        crew.getAttendanceHistory().stream().forEach(attendanceDetail -> {
            stringBuilder.appendLine(generateAttendanceDetail(attendanceDetail));
        });

        stringBuilder.appendLine(
                String.format("%s: %d회", Attendance.출석, crew.getAttendanceHistory().getAttendanceCount()));
        stringBuilder.appendLine(
                String.format("%s: %d회", Attendance.지각, crew.getAttendanceHistory().getTotalLateCount()));
        stringBuilder.appendLine(
                String.format("%s: %d회", Attendance.결석, crew.getAttendanceHistory().getTotalAbsenceCount()));

        AttendanceWarning warning = AttendanceWarning.from(crew.getAttendanceHistory().getAbsenceCount());
        if (!warning.equals(AttendanceWarning.해당없음)) {
            stringBuilder.appendLine(String.format("%s 대상자입니다.", warning.name()));
        }
        stringBuilder.print();
    }

    public void printAttendanceDetail(AttendanceDetail attendanceDetail) {
        System.out.println(generateAttendanceDetail(attendanceDetail));
    }

    public void printModifyResult(AttendanceDetail cloned, AttendanceDetail attendanceDetail) {
        String beforeDetail = generateAttendanceDetail(cloned);
        String afterDetail = generateAttendanceDetail(attendanceDetail);
        System.out.println(String.format("%s -> %s 수정 완료!", beforeDetail, afterDetail));
    }

    private String generateAttendanceDetail(AttendanceDetail attendanceDetail) {
        String dateTime = attendanceDetail.getLocalDateTime().format(normalFormatter);
        if (attendanceDetail.getAttandence().equals(Attendance.결석)) {
            dateTime = attendanceDetail.getLocalDateTime().format(absenceFormatter);
        }
        return String.format("%s (%s)", dateTime, attendanceDetail.getAttandence().name());
    }
}
