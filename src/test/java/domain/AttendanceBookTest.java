package domain;

import domain.policy.AttendancePolicy;
import domain.policy.date.AttendanceDatePolicy;
import domain.policy.time.AttendanceTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.AttendanceFileReader;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceBookTest {

    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            new AttendanceDatePolicy(),
            new AttendanceTimePolicy()
    );

    @Test
    @DisplayName("출석부는 출석 정책을 통해서 초기 상태로 생성할 수 있다.")
    void canInitialize() {
        // given
        // when
        // then
        assertThatCode(() -> AttendanceBook.initialize(attendancePolicy))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("출석부는 원시 값(문자열 포함)으로 구성된 출석 데이터들을 올바르게 그룹화 할 수 있다.")
    void canGroupRawAttendancesData() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initialize(attendancePolicy);
        AttendanceFileReader attendanceFileReader = new AttendanceFileReader();

        // when
        // then
        assertThatCode(() -> attendanceBook.loadAttendance(attendanceFileReader, AttendanceFileReader.DEFAULT_ATTENDANCE_DATA_PATH))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임을 통해서 출석 기록들을 저장하고 조회할 수 있다.")
    void canFindAttendancesByNickname() {
        // given
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);
        AttendanceTime attendanceTime = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        AttendanceBook attendanceBook = AttendanceBook.initialize(attendancePolicy);
        Nickname nickname = Nickname.from("강산");

        // when
        attendanceBook.add(nickname, attendance);
        Attendances attendances = attendanceBook.findByNickname(nickname);

        // then
        assertThat(attendances.findByDate(attendanceDate)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("출석 기록이 존재하지 않는 닉네임을 통해서 출석 기록들을 조회한다면, 예외를 던진다.")
    void whenFindByNonExistsNicknameThrowException() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initialize(attendancePolicy);
        Nickname nickname = Nickname.from("강산");

        // when
        // then
        assertThatThrownBy(() -> attendanceBook.findByNickname(nickname))
                .hasMessageContaining("해당 닉네임으로 출석된 기록이 없습니다");
    }
}
