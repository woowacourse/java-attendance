package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceBookFactory;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatus;
import attendance.domain.SystemDateTime;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;
import attendance.utility.CsvReader;

public class AttendancesTest {
    private final CsvReader csvReader = new CsvReader("/attendances.csv");
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private final AttendanceBookFactory attendanceBookFactory = new AttendanceBookFactory(systemDateTime);
    private final AttendanceBook attendanceBook = attendanceBookFactory.from(csvReader.getLines());

    public AttendancesTest() throws AttendanceFileException {
    }

    @Nested
    @DisplayName("출석하기 테스트")
    class test_register {
        @Test
        @DisplayName("닉네임과 출석 정보을 입력하면, 출석 정보를 저장한다.")
        void test_attendance() {
            var nickname = "이든";
            var dateTime = LocalDateTime.of(2024, 12, 11, 10, 1);

            var attendances = attendanceBook.getAttendances(nickname);
            var newAttendance = Attendance.of(dateTime, systemDateTime);

            attendances.add(newAttendance);
        }

        @Test
        @DisplayName("다시 출석할 경우, 예외가 발생한다.")
        void error_retireAttendance() {
            var nickname = "이든";
            var dateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
            var attendances = attendanceBook.getAttendances(nickname);

            assertThatThrownBy(() -> attendances.validateDuplicate(dateTime.toLocalDate()))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("이미 출석되었습니다. 수정 기능을 이용해주세요.");
        }
    }

    @Nested
    @DisplayName("출석 수정 테스트")
    class test_modify {

        @Test
        @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
        void test_modifyAttendance() {
            var nickname = "이든";
            var dateTime = LocalDateTime.of(2024, 12, 2, 10, 2);

            var attendances = attendanceBook.getAttendances(nickname);
            var newAttendance = Attendance.of(dateTime, systemDateTime);
            Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
            oldAttendance.ifPresent(attendance -> attendances.remove(attendance.getDate()));
            attendances.add(newAttendance);

            Attendance resultAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate())
                .orElseThrow(() -> new AssertionError("잘못된 테스트 입력값입니다."));
            assertAll(
                () -> assertThat(resultAttendance).isNotEqualTo(oldAttendance),
                () -> assertThat(resultAttendance).isEqualTo(newAttendance)
            );
        }

        @Test
        @DisplayName("출석하지 않은 날에 대한 출석을 수정한다.")
        void test_modifyNonAttendanceDay() {
            var nickname = "이든";
            var dateTime = LocalDateTime.of(2024, 12, 11, 10, 1);

            var attendances = attendanceBook.getAttendances(nickname);
            var newAttendance = Attendance.of(dateTime, systemDateTime);

            Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
            attendances.add(newAttendance);

            Attendance resultAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate())
                .orElseThrow(() -> new AssertionError("잘못된 테스트 입력값입니다."));

            assertAll(
                () -> assertThat(oldAttendance.isEmpty()).isTrue(),
                () -> assertThat(resultAttendance).isEqualTo(newAttendance)
            );
        }

        // 해당 테스트는 attendance에 의해 출석 상태가 결정되기에, modifier의 핵심 기능과는 다소 거리가 있다.
        // 때문에, 기존의 단위 테스트와 다른 면이 있어,성공 테스트 케이스에 대해선 크게 세분화하진 않겠다.
        @ParameterizedTest
        @DisplayName("출석이 수정될 경우, 출석 상태도 수정한다")
        @CsvSource({
            "2,13,6, LATE",
            "2,13,31, ABSENCE",
            "10,10,6, LATE",
            "10,10,31, ABSENCE"
        })
        void test_modifyAttendanceState(int dayOfMonth, int hour, int minute, AttendanceStatus expectedStatus) {
            var nickname = "이든";
            var dateTime = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute);

            var attendances = attendanceBook.getAttendances(nickname);
            var newAttendance = Attendance.of(dateTime, systemDateTime);
            Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
            oldAttendance.ifPresent(attendance -> attendances.remove(attendance.getDate()));
            attendances.add(newAttendance);

            Optional<Attendance> resultAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
            assertThat(resultAttendance.get().attendanceStatus()).isEqualTo(expectedStatus);
        }
    }
}
