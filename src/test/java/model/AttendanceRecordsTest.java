package model;

import static org.junit.jupiter.api.Assertions.*;

import controller.Controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceRecordsTest {

    private final AttendanceBook studentRepository = new Controller().createStudentRepository();
    private final Student student = studentRepository.findStudentByName("빙티");

    @BeforeEach
    void studentRepositoryUpdate(){
        student.getAttendanceRecords().updateStateNotExistInFile(LocalDate.of(2024,12,13));
    }

    @Test
    void findTotalAttendanceCount() {
        long expect = 1;
        long result = student.getAttendanceRecords().findTotalAttendanceCount();
        assertEquals(expect, result);
    }

    @Test
    void findTotalLateCount() {
        long expect = 1;
        long result = student.getAttendanceRecords().findTotalLateCount();
        assertEquals(expect, result);
    }

    @Test
    void findTotalAbsentCount() {
        long expect = 10;
        long result = student.getAttendanceRecords().findTotalAbsentCount();
        assertEquals(expect, result);
    }

    @Test
    void createAttendanceRecords() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 13, 9,59);
        student.createAttendanceRecords(today);
        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        AttendanceStatus result = student.getAttendanceRecords().findAttendanceStatusByLocalDateTime(today);

        assertEquals(expect, result);
    }

    @Test
    void updateStateNotExistInFile() {
        student.getAttendanceRecords().updateStateNotExistInFile(LocalDate.of(2024,12,13));
        student.getAttendanceCount().updateAttendanceCount(student.getAttendanceRecords());
        long expect = 10;
        long result = student.getAttendanceCount().getAbsentTotalCount();

        assertEquals(expect, result);
    }

    @Test
    void findAttendanceStatusByLocalDateTime() {
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        AttendanceStatus result = student.getAttendanceRecords()
                .findAttendanceStatusByLocalDateTime(LocalDateTime.of(2024,12,11,9,9));
        assertEquals(expect, result);
    }

}