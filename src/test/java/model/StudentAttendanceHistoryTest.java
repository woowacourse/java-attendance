package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentAttendanceHistoryTest {
    AttendanceDateTime attendanceDateTime1;
    AttendanceDateTime attendanceDateTime2;
    StudentAttendanceHistory studentAttendanceHistory;

    @BeforeEach
    @Test
    void set() {
        attendanceDateTime1 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 0, 0));
        attendanceDateTime2 = new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 0, 0));
        studentAttendanceHistory = new StudentAttendanceHistory(List.of(attendanceDateTime1, attendanceDateTime2));
    }
    @Test
    @DisplayName("시간 추가하는 메서드 테스트")
    void test1() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(LocalDateTime.of(2024, 12, 4, 0, 0));
        studentAttendanceHistory.addTime(attendanceDateTime);
        Assertions.assertTrue(studentAttendanceHistory.getAttendanceHistory().contains(attendanceDateTime));
    }

    @Test
    @DisplayName("같은 날짜 찾는 메서드 테스트")
    void test2() {
        Assertions.assertEquals(
                studentAttendanceHistory.findSameDay(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 0, 0))),
                attendanceDateTime1);
    }

    @Test
    @DisplayName("수정할 날짜 저장되는지에 대한 테스트")
    void test3() {
        studentAttendanceHistory.modifyRecord(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 6)));
        Assertions.assertEquals(
                studentAttendanceHistory.findSameDay(new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 15, 6))),
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 13, 6)));
    }

    @Test
    @DisplayName("오늘 날짜를 기준으로 없는 날짜 업데이트 하는 메서드 테스트")
    void test4() {
        studentAttendanceHistory.fillMissingAttendanceRecords(new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 0, 0)));
        Assertions.assertEquals(
                studentAttendanceHistory.findSameDay(new AttendanceDateTime(LocalDateTime.of(2024, 12, 11, 15, 6))),
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 11, 0, 0)));
    }

    @Test
    @DisplayName("찾는 날짜가 존재하는 판단하는 메서드 테스트")
    void test5() {
        Assertions.assertTrue(studentAttendanceHistory.isAlreadyAttendanceDate(new TodayDate(LocalDate.of(2024, 12, 2))));
    }

    @Test
    @DisplayName("정렬이 됐는지 판단하는 메서드 테스트")
    void test6() {
        studentAttendanceHistory.addTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 14, 0, 0)));
        studentAttendanceHistory.addTime(new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 0, 0)));
        studentAttendanceHistory.sortHistoryBeforePrint();

        List<AttendanceDateTime> sortedHistory = studentAttendanceHistory.getAttendanceHistory();


        Assertions.assertEquals(
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 2, 0, 0)), sortedHistory.get(0));
        Assertions.assertEquals(
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 3, 0, 0)), sortedHistory.get(1));
        Assertions.assertEquals(
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 12, 0, 0)), sortedHistory.get(2));
        Assertions.assertEquals(
                new AttendanceDateTime(LocalDateTime.of(2024, 12, 14, 0, 0)), sortedHistory.get(3));
    }

}