package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordsTest {

    @Test
    @DisplayName("날짜(월일)에 크루의 출석 기록이 있으면 true를 반환한다")
    void exists_true() {
        // given
        String nickname = "name";
        LocalDate checkedDate = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(checkedDate, time);
        AttendanceRecord attendanceRecord = new AttendanceRecord("name", checkedDate, time, status);

        AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
        attendanceRecords.add(attendanceRecord);

        // when & then
        Assertions.assertThat(attendanceRecords.exists(nickname, checkedDate)).isTrue();
    }

    @Test
    @DisplayName("날짜(월일)에 크루의 출석 기록이 없으면 false를 반환한다")
    void exists_false() {
        // given
        String nickname = "name";
        LocalDate checkedDate = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(checkedDate, time);
        AttendanceRecord attendanceRecord = new AttendanceRecord("name", checkedDate, time, status);

        AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
        attendanceRecords.add(attendanceRecord);

        LocalDate uncheckedDate = LocalDate.of(2025, 2, 4);

        // when & then
        Assertions.assertThat(attendanceRecords.exists(nickname, uncheckedDate)).isFalse();
    }

    @Test
    @DisplayName("닉네임과 날짜가 같은 출석 기록이 이미 존재하면 add 실행 시 예외가 발생한다")
    void add_exception() {
        // given
        String nickname = "name";
        LocalDate checkedDate = LocalDate.of(2025, 2, 3);
        LocalTime time = LocalTime.of(13, 0);
        AttendanceStatus status = AttendanceStatus.of(checkedDate, time);
        AttendanceRecord attendanceRecord = new AttendanceRecord("name", checkedDate, time, status);

        AttendanceRecords attendanceRecords = new AttendanceRecords(new ArrayList<>());
        attendanceRecords.add(attendanceRecord);

        // when & then
        Assertions.assertThat(attendanceRecords.exists(nickname, checkedDate)).isTrue();
    }
}