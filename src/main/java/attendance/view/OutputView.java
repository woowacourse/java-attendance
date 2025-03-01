package attendance.view;

import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.domain.PenaltyType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm",
            Locale.KOREA);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE", Locale.KOREA);
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA);

    public void writeAttendanceRegister(AttendanceTime registerdAttendanceTime) {
        LocalDateTime attendanceTime = registerdAttendanceTime.getAttendanceTime();
        AttendanceStatus attendanceStatus = registerdAttendanceTime.getAttendanceStatus();
        String formattedDate = attendanceTime.format(dateTimeFormatter);

        System.out.println(formattedDate + " (" + attendanceStatus.getName() + ")");
    }

    public void writeErrorMessage(String message) {
        System.out.println(message);
    }

    public void writeAttendanceModify(AttendanceTime beforeTime, AttendanceTime updateTime) {
        AttendanceStatus beforeAttendanceStatus = beforeTime.getAttendanceStatus();
        AttendanceStatus updateAttendanceStatus = updateTime.getAttendanceStatus();
        LocalDateTime beforeAttendanceTime = beforeTime.getAttendanceTime();
        LocalDateTime updateAttendanceTime = updateTime.getAttendanceTime();
        String beforeFormat = beforeAttendanceTime.format(dateTimeFormatter);
        String updateFormat = updateAttendanceTime.format(timeFormatter);

        System.out.println(beforeFormat + " (" + beforeAttendanceStatus.getName() + ") -> " +
                updateFormat + " (" + updateAttendanceStatus.getName() + ") 수정 완료!");
    }

    public void writeAttendanceCheck(String crewName, AttendanceRecord attendanceRecord) {
        System.out.println("이번 달 " + crewName + "의 출석 기록입니다.");
        System.out.println();

        for (AttendanceTime attendanceTime : attendanceRecord.getAttendanceRecord()) {
            AttendanceStatus status = attendanceTime.getAttendanceStatus();
            LocalDateTime localDateTime = attendanceTime.getAttendanceTime();
            writeDefaultHyphen(localDateTime, status);
        }
        writeAttendanceRecord(attendanceRecord);
    }

    private static void writeAttendanceRecord(AttendanceRecord attendanceRecord) {
        System.out.println();
        System.out.println("출석: " + attendanceRecord.checkAttendanceCounts() + "회");
        System.out.println("지각: " + attendanceRecord.checkLateCounts() + "회");
        System.out.println("결석: " + attendanceRecord.checkAbsenceCounts() + "회");
        System.out.println();
        writePenaltyStatus(attendanceRecord.checkPenaltyStatus());
    }

    private static void writePenaltyStatus(PenaltyType penaltyType) {
        if (penaltyType.equals(PenaltyType.EXPULSION)) {
            System.out.println("제적 대상자입니다.");
            return;
        }
        if (penaltyType.equals(PenaltyType.COUNSELING)) {
            System.out.println("면담 대상자입니다.");
            return;
        }
        if (penaltyType.equals(PenaltyType.WARNING)) {
            System.out.println("경고 대상자입니다.");
            return;
        }
        System.out.println("성실하게 출석 하셨습니다.");
    }

    private void writeDefaultHyphen(LocalDateTime localDateTime, AttendanceStatus status) {
        if (localDateTime.getHour() == 0 && localDateTime.getMinute() == 0) {
            System.out.println(localDateTime.format(dateFormatter) + " --:-- " + "(" + status.getName() + ")");
            return;
        }
        System.out.println(localDateTime.format(dateTimeFormatter) + " (" + status.getName() + ")");
    }
}
