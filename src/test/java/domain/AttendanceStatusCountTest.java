package domain;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusCountTest {
    AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount();

    @BeforeEach
    void setUp() {
        //기본 상태 : 출석 2, 지각 1, 결석 2
        Attendance attendance1 = new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0));
        Attendance attendance2 = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0));
        Attendance attendance3 = new Attendance(LocalDateTime.of(2024, 12, 4, 11, 0));
        Attendance attendance4 = new Attendance(LocalDateTime.of(2024, 12, 5, 10, 20));
        Attendance attendance5 = new Attendance(LocalDateTime.of(2024, 12, 6, 12, 0));

        attendanceStatusCount.addStatus(attendance1);
        attendanceStatusCount.addStatus(attendance2);
        attendanceStatusCount.addStatus(attendance3);
        attendanceStatusCount.addStatus(attendance4);
        attendanceStatusCount.addStatus(attendance5);
    }

    @DisplayName("출석에 따른 상태 횟수를 업데이트합니다.")
    @Test
    void test1() {
        Attendance attendanceLate = new Attendance(LocalDateTime.of(2024, 12, 9, 13, 6));
        attendanceStatusCount.addStatus(attendanceLate);

        Map<AttendanceStatus, Integer> statuses = attendanceStatusCount.getStatuses();
        Assertions.assertEquals(2, statuses.get(AttendanceStatus.LATE));
    }

    @DisplayName("출석에 따른 상태 횟수를 삭제합니다.")
    @Test
    void test2() {
        Attendance attendanceLate = new Attendance(LocalDateTime.of(2024, 12, 9, 13, 6));
        attendanceStatusCount.deleteStatus(attendanceLate);

        Map<AttendanceStatus, Integer> statuses = attendanceStatusCount.getStatuses();
        Assertions.assertEquals(0, statuses.get(AttendanceStatus.LATE));
    }

    @DisplayName("상태에 따른 제적 위험 결과를 반환합니다.")
    @Test
    void test3() {
        AttendanceAlertLevel result = attendanceStatusCount.calculateAttendanceAlertLevel();
        Assertions.assertEquals(AttendanceAlertLevel.CAUTION, result);
    }
}