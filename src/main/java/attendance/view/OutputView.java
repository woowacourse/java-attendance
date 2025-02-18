package attendance.view;

import attendance.model.Attendance;
import attendance.model.AttendanceWarning;
import attendance.model.Crew;
import java.time.format.DateTimeFormatter;

public class OutputView {

    public void printAttendanceHistory(Crew crew) {
        CustomStringBuilder stringBuilder = new CustomStringBuilder();
        stringBuilder.appendLine(String.format("이번 달 %s의 출석 기록입니다.", crew.getName()));

        crew.getAttendanceHistory().stream().forEach(attendanceDetail -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
            if(attendanceDetail.getAttandence().equals(Attendance.결석)) {
                formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");
            }

            String dateTime = attendanceDetail.getLocalDateTime().format(formatter);
            stringBuilder.appendLine(String.format("%s (%s)",dateTime, attendanceDetail.getAttandence().name()));
        });

        stringBuilder.appendLine(String.format("%s: %d회", Attendance.출석, crew.getAttendanceHistory().getAttendanceCount()));
        stringBuilder.appendLine(String.format("%s: %d회", Attendance.지각, crew.getAttendanceHistory().getTotalLateCount()));
        stringBuilder.appendLine(String.format("%s: %d회", Attendance.결석, crew.getAttendanceHistory().getTotalAbsenceCount()));

        AttendanceWarning warning = AttendanceWarning.from(crew.getAttendanceHistory().getAbsenceCount());
        if(!warning.equals(AttendanceWarning.해당없음)) {
            stringBuilder.appendLine(String.format("%s 대상자입니다.", warning.name()));
        }
        stringBuilder.print();
    }

}
