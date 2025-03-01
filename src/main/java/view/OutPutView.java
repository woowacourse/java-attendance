package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import model.AttendancePenalty;
import model.AttendanceStatus;
import model.Student;
import util.AttendanceRecordFormatter;

public class OutPutView {
    public static void displayAttendanceMenu(LocalDate localDate) {
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요\n",
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        System.out.println(
                "1. 출석 확인\n"
                        + "2. 출석 수정\n"
                        + "3. 크루별 출석 기록 확인\n"
                        + "4. 제적 위험자 확인\n"
                        + "Q. 종료\n");
    }

    public static void requestNickName() {
        System.out.println("닉네임을 입력해 주세요.");
    }

    public static void requestAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
    }

    public static void requestModifyNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public static void requestModifyDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public static void requestModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
    }

    public static void displayModifyAttendanceRecord(String beforeRecord, String modifyRecord) {
        System.out.println(beforeRecord + "->" + modifyRecord + "수정 완료!");
    }

    public static void displayRegisterAttendanceRecord(String record) {
        System.out.println(record);
    }

    public static void displayTotalAttendanceRecord(Student student) {
        Map<LocalDate, LocalTime> timeRecord = student.getAttendanceTimeRecords();
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", student.getName());
        for (LocalDate localDate : timeRecord.keySet()) {
            System.out.println(AttendanceRecordFormatter.attendanceRecordFormatter(
                    student, localDate));
        }
    }

    public static void displayTotalAttendanceCount(Student student) {
        Map<AttendanceStatus, Long> attendanceCount = student.getAttendanceStatusCount();
        System.out.println();
        for (AttendanceStatus attendanceStatus : attendanceCount.keySet()) {
            System.out.println(
                    attendanceStatus.getAttendanceStatus() + " : " + attendanceCount.get(attendanceStatus) + "회");
        }
    }

    public static void displayCounselingCandidate(Student student) {
        long expulsionCount = student.calculateTotalAbsentCount();
        AttendancePenalty attendancePenalty = AttendancePenalty.findPenaltyByAbsentCount(expulsionCount);
        if (attendancePenalty.getPenalty() != null) {
            System.out.println(attendancePenalty.getPenalty() + "대상자 입니다.");
        }
    }

    public static void displayExpulsionRiskStudents(List<Student> students) {
        for (Student student : students) {
            System.out.println(AttendanceRecordFormatter.expulsionRiskRecordFormatter(student));
        }
    }
}
