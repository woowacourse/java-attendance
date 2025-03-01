package domain;

import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.TimeMachine;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {

    @Test
    @DisplayName("출석을 추가할 수 있다.")
    void canAddAttendance() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.of(10, 10));
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        // when
        attendances.add(attendance);

        // then
        assertThat(attendances.findByDate(attendanceDate).getAttendanceTime())
                .isEqualTo(attendanceTime);
    }

    @Test
    @DisplayName("출석을 등록했다면, 날짜를 통해서 출석 존재를 알 수 있다.")
    void whenAddAttendanceCanCheckExistByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.of(10, 10));
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        // then
        assertThat(attendances.existsByDate(attendanceDate)).isTrue();
    }

    @Test
    @DisplayName("출석을 등록하지 않았다면, 날짜를 통해서 출석 존재를 알 수 없다.")
    void whenNotAddAttendanceCannotCheckExistByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));

        // when
        // then
        assertThat(attendances.existsByDate(attendanceDate)).isFalse();
    }

    @Test
    @DisplayName("출석을 등록했다면, 날짜를 통해서 출석을 찾을 수 있다.")
    void whenAddAttendanceCanCheckFindByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.of(10, 10));
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        // then
        assertThat(attendances.findByDate(attendanceDate)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("출석을 등록하지 않았다면, 날짜를 통해서 출석 존재를 알 수 없다.")
    void whenNotAddAttendanceCannotFindByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));

        // when
        // then
        assertThatThrownBy(() -> attendances.findByDate(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 날짜에 출석 기록이 없습니다.");
    }

    @Test
    @DisplayName("출석 카운트를 계산할 수 있다.")
    void canCalculateAttendanceCounts() {
        // given
        TimeMachine.timeTravelAt(13);

        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.of(10, 0));
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        AttendanceCounts attendanceCounts = attendances.calculateAttendanceCounts(Nickname.from("강산"));

        // then
        assertThat(attendanceCounts.getCount(AttendanceStateRule.ATTEND)).isEqualTo(1);
        assertThat(attendanceCounts.getCount(AttendanceStateRule.LATE)).isEqualTo(0);
        assertThat(attendanceCounts.getCount(AttendanceStateRule.ABSENT)).isEqualTo(8); // 12 - 1(출석) - 3(주말) = 8
    }

    @Test
    @DisplayName("출석을 수정할 수 있다.")
    void canUpdateAttendance() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));

        attendances.add(Attendance.of(
                attendanceDate,
                AttendanceTime.from(LocalTime.of(10, 10))
        ));

        // when
        Attendance updatedAttendance = attendances.update(Attendance.of(
                attendanceDate,
                AttendanceTime.from(LocalTime.of(11, 11))
        ));

        // then
        assertThat(attendances.findByDate(attendanceDate)).isEqualTo(updatedAttendance);
    }

    @Test
    @DisplayName("오늘 이미 출석한 상태라면, 출석할 수 없다.")
    void cannotAttendWhenAlreadyAttended() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.from(LocalDate.of(2024, 12, 12));
        AttendanceTime attendanceTime = AttendanceTime.from(LocalTime.of(10, 10));
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        // then
        assertThatThrownBy(() -> attendances.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 출석한 경우, 수정 기능을 이용해주세요.");
    }

    @Test
    @DisplayName("출석하지 않는 날은 출석을 수정할 수 없다.")
    void cannotUpdateWhenNotAttended() {
        // given
        Attendances attendances = Attendances.initialize();

        // when
        // then
        assertThatThrownBy(() -> attendances.update(Attendance.of(
                AttendanceDate.from(LocalDate.of(2024, 12, 12)),
                AttendanceTime.from(LocalTime.of(11, 11))
        ))).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("출석하지 않은 경우, 수정 기능을 이용할 수 없습니다.");
    }
}
