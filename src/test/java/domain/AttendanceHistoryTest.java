package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import dto.AttendanceData;
import dto.ModifyResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @DisplayName("출석 기록을 저장한다.")
    @Test
    void test1() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendance(LocalDateTime.of(2024, 12, 2, 9, 59));

        AttendanceData attendanceData = attendanceHistory.getAttendanceHistory(LocalDate.of(2024, 12, 2));

        assertEquals(1, attendanceData.value().size());
    }

    @DisplayName("출석을 기록하지 않은 날이 있을 시, 이 날짜를 결석으로 간주하여 저장한다.")
    @Test
    void test2() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceData attendanceData = attendanceHistory.getAttendanceHistory(LocalDate.of(2024, 12, 2));
        Attendance attendance = attendanceData.value().getFirst();

        assertEquals(1, attendanceData.value().size());
        assertEquals(Status.ABSENCE, attendance.getStatus());
    }

    @DisplayName("동일한 날짜에 출석을 재시도할 시 예외가 발생한다.")
    @Test
    void test4() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 5);

        attendanceHistory.addAttendance(LocalDateTime.of(date, LocalTime.of(8, 55)));

        assertThatThrownBy(() -> attendanceHistory.addAttendance(LocalDateTime.of(date, LocalTime.of(9, 55))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("출석 기록을 수정할 수 있다.")
    @Test
    void test5() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDateTime originalDateTime = LocalDateTime.of(2024, 12, 5, 8, 55);
        LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 5, 10, 6);

        attendanceHistory.addAttendance(originalDateTime);
        ModifyResult modifyResult = attendanceHistory.update(newDateTime);

        assertEquals(modifyResult.getOriginalDateAndTime(), originalDateTime);
        assertEquals(modifyResult.getNewDateAndTime(), newDateTime);
    }

    @DisplayName("존재하지 않는 출석 수정을 시도할 시 예외가 발생한다.")
    @Test
    void test6() {
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        assertThatThrownBy(() -> attendanceHistory.update(LocalDateTime.of(2024, 12, 5, 10, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
