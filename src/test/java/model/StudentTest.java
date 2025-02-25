package model;

import static org.junit.jupiter.api.Assertions.*;

import controller.Controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentTest {
    private final AttendanceBook studentRepository = new Controller().createStudentRepository();
    Student student = studentRepository.findStudentByName("빙티");

    @BeforeEach
    void studentRepositoryUpdate(){
        student.getAttendanceRecords().updateStateNotExistInFile(LocalDate.of(2024,12,13));
    }

    @Test
    @DisplayName("출결 등록 테스트")
    void attendanceRegister() {
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,16,13,6);
        student.attendanceRegister(localDateTime);
        AttendanceStatus expect = AttendanceStatus.LATE;
        AttendanceStatus result = student.getAttendanceRecords().findAttendanceStatusByLocalDateTime(localDateTime);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("학생 출결 기록 수정 테스트")
    void modifyAttendanceRecord() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024,12,16,12,59);
        student.attendanceRegister(attendanceTime);
        AttendanceStatus expect = AttendanceStatus.ABSENT;

        LocalDateTime absentTime = LocalDateTime.of(2024,12,16,13,31);
        student.modifyAttendanceRecord(absentTime);
        AttendanceStatus result = student.getAttendanceRecords().findAttendanceStatusByLocalDateTime(attendanceTime);
        assertEquals(expect, result);
    }

    @Test
    void findStateByLocalDateTime() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024,12,16,12,59);
        student.attendanceRegister(attendanceTime);
        String expect = "출석";
        String result = student.findStateByLocalDateTime(attendanceTime);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("지각 3회당 결석 1회 추가 테스트")
    void calculateAbsent(){
        student.attendanceRegister(
                LocalDateTime.of(2024,12,16,13,6));
        student.attendanceRegister(
                LocalDateTime.of(2024,12,17,10,6));
        student.attendanceRegister(
                LocalDateTime.of(2024,12,18,10,6));
        long expect = 11;
        long result = student.calculateAbsent();

        long lateCount = student.getAttendanceCount().getLateTotalCount();
        System.out.println(lateCount);

        assertEquals(expect, result);
    }
}