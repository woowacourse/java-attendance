import domain.Attendance;
import dto.result.AttendResult;
import dto.result.AttendanceModifyResult;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.exception.IllegalAttendDateException;
import util.exception.IllegalAttendTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceTest {

    @Nested
    class 등교_테스트 {

        @Test
        void 닉네임과_등교_시간을_입력하면_출석() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 5);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceDateTime").isEqualTo(LocalDateTime.of(2024, 12, 3, 10, 5));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("출석");
        }

        @Test
        void 월요일은_13시_5분까지_출석이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 5);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceDateTime").isEqualTo(LocalDateTime.of(2024, 12, 2, 13, 5));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("출석");
        }

        @Test
        void 출석_시간보다_5분_초과_늦으면_지각() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 6);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }

        @Test
        void 월요일은_13시_6분부터_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 6);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceDateTime").isEqualTo(LocalDateTime.of(2024, 12, 2, 13, 6));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }

        @Test
        void 출석_시간보다_5분_초과_30분_이하_늦으면_지각() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 30);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }

        @Test
        void 월요일은_13시_30분까지_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceDateTime").isEqualTo(LocalDateTime.of(2024, 12, 2, 13, 30));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }

        @Test
        void 출석_시간보다_30분_초과_늦으면_지각() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("결석");
        }

        @Test
        void 월요일은_13시_31분부터_결석이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 31);

            // when
            Attendance attendance = new Attendance(localDateTime);

            // then
            assertThat(attendance).extracting("attendanceDateTime").isEqualTo(LocalDateTime.of(2024, 12, 2, 13, 31));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("결석");
        }

        @Test
        void 주말에_출석하면_예외가_발생한다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 8, 10, 30);

            // expected
            assertThatThrownBy(() -> new Attendance(localDateTime))
                    .isExactlyInstanceOf(IllegalAttendDateException.class)
                    .hasMessage("출석 가능한 날짜가 아닙니다.");
        }

        @Test
        void _8시와_23시_사이가_아니면_예외가_발생한다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 7, 59);

            // expected
            assertThatThrownBy(() -> new Attendance(localDateTime))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다.");
        }

        @Test
        void 출석을_완료하면_출석기록이_출력된다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(localDateTime);

            // when
            AttendResult result = attendance.createAttendanceResult();

            //then
            assertThat(result.attendanceDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 3, 9, 45));
            assertThat(result.attendanceStatus()).isEqualTo("출석");
        }
    }

    @Nested
    class 수정_테스트 {

        @Test
        void 등교_시간을_수정한다() {
            //given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(localDateTime);
            LocalTime newAttendanceTime = LocalTime.of(10, 6);

            //when
            AttendanceModifyResult result = attendance.modifyAttendanceTime(newAttendanceTime);

            //then

            SoftAssertions.assertSoftly(softly
                    -> {
                softly.assertThat(result.attendanceDate()).isEqualTo(LocalDate.of(2024, 12, 3));
                softly.assertThat(result.oldAttendanceTime()).isEqualTo(LocalTime.of(9, 45));
                softly.assertThat(result.oldAttendanceStatus()).isEqualTo("출석");
                softly.assertThat(result.newAttendanceTime()).isEqualTo(LocalTime.of(10, 6));
                softly.assertThat(result.newAttendanceStatus()).isEqualTo("지각");
            });
        }
    }
}
