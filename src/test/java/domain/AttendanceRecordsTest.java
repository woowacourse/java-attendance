package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceRecordsTest {
    @DisplayName("새 출석 기록을 저장할 수 있다.")
    @Test
    void addTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateTime);

        // then
        assertThatNoException().isThrownBy(() -> attendanceRecords.add(attendanceRecord));
    }

    @DisplayName("주어진 날짜에 출석 기록이 존재하는지 여부를 반환한다.")
    @Test
    void hasRecordOnDateTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 10);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateTime);

        // when
        attendanceRecords.add(attendanceRecord);

        // then
        assertThat(attendanceRecords.hasRecordOnDate(LocalDate.of(2024, 12, 2))).isTrue();
    }

    @DisplayName("총 출석 횟수를 반환한다.")
    @Test
    void getPresentCountTest() {
        // given
        AttendanceStatus targetStatus = AttendanceStatus.PRESENT;
        AttendanceRecords attendanceRecords = new AttendanceRecords();

        // when
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-03T10:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-04T10:00")));

        // then
        assertThat(attendanceRecords.getAttendanceCount(targetStatus)).isEqualTo(3);
    }

    @DisplayName("총 지각 횟수를 반환한다.")
    @Test
    void getTardyCountTest() {
        // given
        AttendanceStatus targetStatus = AttendanceStatus.TARDY;
        AttendanceRecords attendanceRecords = new AttendanceRecords();

        // when
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:10")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-03T10:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-04T10:20")));

        // then
        assertThat(attendanceRecords.getAttendanceCount(targetStatus)).isEqualTo(2);
    }

    @DisplayName("총 결석 횟수를 반환한다.")
    @Test
    void getAbsentCountTest() {
        // given
        AttendanceStatus targetStatus = AttendanceStatus.ABSENT;
        AttendanceRecords attendanceRecords = new AttendanceRecords();

        // when
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-03T10:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-04T10:40")));

        // then
        assertThat(attendanceRecords.getAttendanceCount(targetStatus)).isEqualTo(1);
    }

    @DisplayName("새 출석 기록 추가 시 해당 날짜에 이미 기록이 존재하면 예외를 발생시킨다.")
    @Test
    void addExceptionTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:10"));

        // when
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-03T10:00")));
        attendanceRecords.add(new AttendanceRecord(LocalDateTime.parse("2024-12-04T10:40")));

        // then
        assertThatThrownBy(() -> attendanceRecords.add(record)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력 받은 날짜의 출석 시간을 수정할 수 있다.")
    @Test
    void updateTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        AttendanceRecord oldRecord = new AttendanceRecord(LocalDateTime.parse("2024-12-02T13:10"));
        attendanceRecords.add(oldRecord);
        LocalTime newTime = LocalTime.of(13, 0);
        LocalDate oldDate = LocalDate.of(2024, 12, 2);

        // when
        attendanceRecords.update(oldDate, newTime);

        // then
        assertAll(
                () -> assertThat(oldRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.TARDY),
                () -> assertThat(attendanceRecords.getRecordOnDate(oldDate).getAttendanceStatus()).isEqualTo(AttendanceStatus.PRESENT)
        );
    }

    @DisplayName("주어진 날짜의 출석 기록을 삭제할 수 있다.")
    @Test
    void removeTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        LocalDateTime dateTime = LocalDateTime.parse("2024-12-02T13:10");
        AttendanceRecord record = new AttendanceRecord(dateTime);
        attendanceRecords.add(record);

        // when
        attendanceRecords.remove(record);

        // then
        assertThat(attendanceRecords.hasRecordOnDate(dateTime.toLocalDate())).isFalse();
    }

    @DisplayName("기록이 없는 날짜의 출석 시간을 수정하려고 할 경우 예외가 발생한다.")
    @Test
    void updateExceptionTest() {
        // given
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(10, 0);

        // then
        assertThatThrownBy(() -> attendanceRecords.update(date, time)).isInstanceOf(IllegalArgumentException.class);
    }
}
