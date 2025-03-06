import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import domain.Attendance;
import domain.AttendanceStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTest {

    @DisplayName("출석 시간과 출석 상태를 수정할 수 있다. 만약, 출석시간이 null이라면, 새로 생성한다")
    @Test
    void updateAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 10);

        int updateHour = 10;
        int updateMinute = 0;
        AttendanceStatus updateAttendanceStatus = AttendanceStatus.ATTENDANCE;
        //when
        attendance.updateAttendance(updateHour, updateMinute, updateAttendanceStatus);

        // then
        assertSoftly(softly -> {
            softly.assertThat(attendance).extracting("attendanceTime").extracting("hour").isEqualTo(10);
            softly.assertThat(attendance).extracting("attendanceTime").extracting("minute").isEqualTo(0);
            softly.assertThat(attendance).extracting("attendanceStatus").isEqualTo(AttendanceStatus.ATTENDANCE);
        });
    }

    @DisplayName("출석 시간이 없고 결석 상태인 객체를 생성한다")
    @Test
    void createAbsenceAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);

        //when
        Attendance attendance = Attendance.createAbsenceAttendance(localDate);

        //then
        assertSoftly(softly -> {
            softly.assertThat(attendance).extracting("attendanceTime").isNull();
            softly.assertThat(attendance).extracting("attendanceStatus").isEqualTo(AttendanceStatus.ABSENCE);
        });
    }

    @DisplayName("출석 날짜와 입력객체 날짜가 같다면 true를 반환한다")
    @Test
    void isEqualDateByAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        Attendance attendance2 = AttendanceFixture.createAttendance(localDate, 10, 0);

        //when
        boolean isEqualDate = attendance.isEqualDate(attendance2);

        //then
        assertThat(isEqualDate).isTrue();
    }

    @DisplayName("출석 날짜와 입력객체 날짜가 다르다면 false를 반환한다")
    @Test
    void isNotEqualDateByAttendance() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        LocalDate anotherDate = LocalDate.of(2024, 12, 14);

        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        Attendance attendance2 = AttendanceFixture.createAttendance(anotherDate, 10, 0);

        //when
        boolean isEqualDate = attendance.isEqualDate(attendance2);

        //then
        assertThat(isEqualDate).isFalse();
    }

    @DisplayName("출석 날짜와 입력 날짜가 같다면 true를 반환한다")
    @Test
    void isEqualDateByDate() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        LocalDate localDate2 = LocalDate.of(2024, 12, 13);

        //when
        boolean isEqualDate = attendance.isEqualDate(localDate2);

        //then
        assertThat(isEqualDate).isTrue();
    }

    @DisplayName("출석 날짜와 입력 날짜가 다르다면 true를 반환한다")
    @Test
    void isNotEqualDateByDate() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);

        LocalDate anotherDate = LocalDate.of(2024, 12, 14);
        //when
        boolean isEqualDate = attendance.isEqualDate(anotherDate);

        //then
        assertThat(isEqualDate).isFalse();
    }

    @DisplayName("출석 날짜가 입력 날짜보다 작다면 true를 반환한다")
    @Test
    void isBeforeDate() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        LocalDate inputDate = LocalDate.of(2024, 12, 14);

        // when
        boolean isBefore = attendance.isBeforeDate(inputDate);

        //then
        assertThat(isBefore).isTrue();
    }

    @DisplayName("출석 날짜가 입력 날짜보다 같거나 크다면 false를 반환한다")
    @ParameterizedTest
    @ValueSource(ints = {12, 13})
    void isNotBefore(int value) {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = AttendanceFixture.createAttendance(localDate, 10, 0);
        LocalDate inputDate = LocalDate.of(2024, 12, value);

        // when
        boolean isBefore = attendance.isBeforeDate(inputDate);

        //then
        assertThat(isBefore).isFalse();
    }
}
