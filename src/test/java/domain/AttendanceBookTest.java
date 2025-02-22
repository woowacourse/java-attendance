package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @Test
        @DisplayName("출석 시스템을 생성한다.")
        void test1() {
            //given
            final List<String> data = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");

            //when
            final AttendanceBook attendanceBook = AttendanceBook.of(data, LocalDate.of(2024, 12, 14));
            final List<Crew> crews = attendanceBook.getCrews();

            //then
            assertThat(crews).hasSize(2);
        }

        @Test
        @DisplayName("출석 시스템에 등록된 크루원인지 닉네임으로 검사한다.")
        void test2() {
            //given
            final List<String> data = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, LocalDate.of(2024, 12, 14));
            final String expectedCrewName = "쿠키";

            //when
            final Crew crew = attendanceBook.findCrewByName(expectedCrewName);

            //then
            assertThat(crew.getName().getName()).isEqualTo(expectedCrewName);
        }

        @Test
        @DisplayName("출석을 생성한다.")
        void attendance() {
            //given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final LocalTime time = LocalTime.of(10, 30);
            final LocalDateTime attendancedTime = LocalDateTime.of(today, time);
            final List<String> data = List.of("쿠키,2024-12-13 10:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);
            final Attendance expectedAttendance = Attendance.of(attendancedTime);

            //when
            final Attendance actual = attendanceBook.attendance("쿠키", attendancedTime);

            //then
            assertThat(actual).isEqualTo(expectedAttendance);
        }

        @Test
        @DisplayName("오늘 이미 출석했는지 여부를 반환한다.")
        void isAlreadyTodayAttendance() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final LocalTime time = LocalTime.of(10, 30);
            final LocalDateTime attendancedTime = LocalDateTime.of(today, time);
            final List<String> data = List.of("쿠키,2024-12-13 10:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);
            attendanceBook.attendance("쿠키", attendancedTime);

            // when
            boolean actual = attendanceBook.isAlreadyTodayAttendance("쿠키", today);

            // then
            assertThat(actual).isTrue();
        }

        @Test
        @DisplayName("경고를 받은 크루원들을 계산해서 반환한다.")
        void calculateExpulsionCrews() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 14);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when
            final List<Crew> crews = attendanceBook.calculateExpulsionCrews();

            // then
            assertThat(crews).isNotEmpty();
            assertThat(crews.getFirst().getName().getName()).isEqualTo("쿠키");
        }

        @Test
        @DisplayName("크루 이름과 날짜에 해당하는 출석을 수정한다.")
        void updateAttendanceByCrewNameAndDay() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 14);
            final LocalTime time = LocalTime.of(10, 30);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when
            Attendance attendance = attendanceBook.updateAttendanceByCrewNameAndDay(time, "쿠키", 13);

            // then
            assertThat(attendance.getDateTime().toLocalTime()).isEqualTo(LocalTime.of(11, 8));
        }


        @Test
        @DisplayName("크루 이름에 해당하는 크루를 반환한다.")
        void findCrewByName() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when
            Crew actual = attendanceBook.findCrewByName("쿠키");

            // then
            assertThat(actual.getName().getName()).isEqualTo("쿠키");
        }

        @Test
        @DisplayName("크루가 존재하므로 예외가 발생하지 않는다")
        void validateCrewByNameTest() {
            //given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            //when
            //then
            assertThatCode(() -> attendanceBook.validateCrewByName("쿠키")).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("유효한 날짜이므로 예외가 발생하지 않는다")
        void validateUpdateAttendanceDayTest() {
            //given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            //when
            //then
            assertThatCode(() -> attendanceBook.validateUpdateAttendanceDay("쿠키", 10)).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("주말과 공휴일이 아니므로 등교일이다.")
        void isNotAttendanceDayTest() {
            //given
            final LocalDate localDate = LocalDate.of(2024, 12, 2);
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            //when
            final boolean result = attendanceBook.isNotAttendanceDay(localDate);
            //then
            assertThat(result).isFalse();
        }

    }


    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @Test
        @DisplayName("크루가 존재하지 않는다면, 예외가 발생한다.")
        void validateCrewByName() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 13);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when & then
            assertThatThrownBy(() -> {
                attendanceBook.validateCrewByName("감자");
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("수정할 수 없는 날짜라면, 예외가 발생한다.")
        void validateUpdateAttendanceDay() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 25);
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when & then
            assertThatThrownBy(() -> {
                attendanceBook.validateUpdateAttendanceDay("쿠키", 25);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("주말과 공휴일(크리스마스) 날짜를 출석을 하여 예외가 발생한다.")
        void updateAttendanceByCrewNameAndDayTest() {
            // given
            final LocalDate today = LocalDate.of(2024, 12, 26);
            final LocalDateTime time = LocalDateTime.of(2024, 12,25, 10,10 );
            final List<String> data = List.of("쿠키,2024-12-13 11:08", "쿠키,2024-12-12 11:08", "쿠키,2024-12-11 11:08");
            final AttendanceBook attendanceBook = AttendanceBook.of(data, today);

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy(() -> attendanceBook.attendance("쿠키", time));
        }

    }
}
