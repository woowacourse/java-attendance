package domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCountTest {
    @DisplayName("출석, 지각, 결석을 정확하게 계산해 저장하는지 확인합니다.")
    @Test
    void CalculateRecordAttendanceTest() {
        AttendanceCount attendanceCount = new AttendanceCount();
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0));

        attendanceCount.calculateRecord(attendance);
        assertAll(
                () -> Assertions.assertEquals(1, attendanceCount.getPresent()),
                () -> Assertions.assertEquals(0, attendanceCount.getAbsent()),
                () -> Assertions.assertEquals(0, attendanceCount.getLate())
        );
    }

    @DisplayName("출석이 현재 일반 상태인지 계산합니다.")
    @Test
    void calculateAttendanceAlertLevelNormalTest() {
        AttendanceCount attendanceCount = new AttendanceCount();
        List<Attendance> testAttendances = new ArrayList<>();

        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 10)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 9, 13, 0)));

        for (Attendance attendance : testAttendances) {
            attendanceCount.calculateRecord(attendance);
        }
        Assertions.assertEquals(AttendanceAlertLevel.NORMAL, attendanceCount.calculateAttendanceAlertLevel());
    }

    @DisplayName("출석이 현재 경고 상태인지 계산합니다.")
    @Test
    void calculateAttendanceAlertLevelCautionTest() {
        AttendanceCount attendanceCount = new AttendanceCount();
        List<Attendance> testAttendances = new ArrayList<>();

        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 6)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 6)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 10, 30)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 13, 0)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));

        for (Attendance attendance : testAttendances) {
            attendanceCount.calculateRecord(attendance);
        }
        Assertions.assertEquals(AttendanceAlertLevel.CAUTION, attendanceCount.calculateAttendanceAlertLevel());
    }

    @DisplayName("출석이 현재 면담 상태인지 계산합니다.")
    @Test
    void calculateAttendanceAlertLevelCounselTest() {
        AttendanceCount attendanceCount = new AttendanceCount();
        List<Attendance> testAttendances = new ArrayList<>();

        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 10, 10)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 13, 0)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));

        for (Attendance attendance : testAttendances) {
            attendanceCount.calculateRecord(attendance);
        }
        Assertions.assertEquals(AttendanceAlertLevel.COUNSEL_REQUIRED, attendanceCount.calculateAttendanceAlertLevel());
    }

    @DisplayName("출석이 현재 제적 상태인지 계산합니다.")
    @Test
    void calculateAttendanceAlertLevelDismissTest() {
        AttendanceCount attendanceCount = new AttendanceCount();
        List<Attendance> testAttendances = new ArrayList<>();

        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 4, 10, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 5, 13, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 6, 10, 31)));
        testAttendances.add(new Attendance(LocalDateTime.of(2024, 12, 9, 13, 31)));

        for (Attendance attendance : testAttendances) {
            attendanceCount.calculateRecord(attendance);
        }
        Assertions.assertEquals(AttendanceAlertLevel.DISMISSED, attendanceCount.calculateAttendanceAlertLevel());
    }
}