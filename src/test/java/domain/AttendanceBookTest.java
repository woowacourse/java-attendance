package domain;

import domain.policy.AttendancePolicy;
import domain.policy.date.AttendanceDatePolicy;
import domain.policy.time.AttendanceTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reader.AttendanceFileReader;

import static org.assertj.core.api.Assertions.assertThatCode;

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
}
