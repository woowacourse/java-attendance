package domain;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class CrewAttendanceTest {

    private CrewAttendance sut;

    @BeforeEach
    void setUp() {
        sut = new CrewAttendance(LocalDate.of(2024, 12, 30));
    }

    @Nested
    class 생성_테스트 {

        @Test
        void 출석_가능한_날짜중_오늘까지의_날짜를_모두_갖는다() {
            //given
            var today = LocalDate.of(2024, 12, 5);

            //when
            var result = new CrewAttendance(today);

            //then
            assertThat(result.getAllAttendances())
                    .extracting("attendDate")
                    .containsExactlyInAnyOrder(
                            LocalDate.of(2024, 12, 2), LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 4), LocalDate.of(2024, 12, 5)
                    );
        }
    }

    @Nested
    class 출석_확인_테스트 {

        private final LocalDate monday = LocalDate.of(2024, 12, 2);
        private final LocalDate notMonday = LocalDate.of(2024, 12, 3);

        @Test
        void 시간과_날짜를_입력하여_출석한다() {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.of(10, 5);
            
            //expected
            assertThatCode(() -> sut.attend(date, time))
                    .doesNotThrowAnyException();
        }
        
        @Test
        void 출석하면_해당_날짜의_출석_기록이_저장된다() {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.of(10, 5);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date)).isNotNull();
        }

        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);

            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.출석);
        }

        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.지각);
        }

        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.결석);
        }

        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.출석);
        }

        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.지각);
        }

        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석이다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(date, time);
            
            //then
            assertThat(getAttendanceOf(date).getStatus()).isEqualTo(AttendanceStatus.결석);
        }

        @ParameterizedTest
        @CsvSource({"07:56", "07:57", "07:58", "07:59"})
        void _8시_이전으로_출석하려고하면_예외가_발생한다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeValue);
            
            //expected
            assertThatThrownBy(() -> sut.attend(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }

        @ParameterizedTest
        @CsvSource({"23:01", "23:02", "23:03", "23:04"})
        void _23시_이후로_출석하려고하면_예외가_발생한다(String timeValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeValue);
            
            //expected
            assertThatThrownBy(() -> sut.attend(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }

        @Test
        void 출석이_가능한_달이_아니면_예외가_발생한다() {
            //given
            var date = LocalDate.of(2024, 11, 2);
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }

        @Test
        void 주말이면_예외가_발생한다() {
            //given
            var date = LocalDate.of(2024, 12, 1);
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }

        @Test
        void 공휴일이면_예외가_발생한다() {
            //given
            var date = LocalDate.of(2024, 12, 25);
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        private Attendance getAttendanceOf(final LocalDate date) {
            return sut.getAllAttendances().stream()
                    .filter(attendance -> attendance.getAttendDate() == date)
                    .findFirst()
                    .orElse(null);
        }
    }
    
    @Nested
    class 출석_수정_테스트 {

        @Test
        void 출석을_수정하면_수정된_기록을_확인할_수_있다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 5);
            var oldTime = LocalTime.of(10, 10);
            var newTime = LocalTime.of(9, 45);
            sut.attend(targetDate, oldTime);

            //when
            sut.modify(targetDate, newTime);

            //then
            var attendance = getAttendanceOf(targetDate);
            assertAll(
                    () -> assertThat(attendance.getAttendDate()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(attendance.getAttendTime().getAttendTime()).isEqualTo(LocalTime.of(9, 45)),
                    () -> assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.출석)
            );
        }

        @Test
        void 기존에_출석하지_않았어도_수정할_수_있다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(9, 45);

            //when
            sut.modify(targetDate, newTime);

            //then
            var attendance = getAttendanceOf(targetDate);
            assertAll(
                    () -> assertThat(attendance.getAttendDate()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(attendance.getAttendTime().getAttendTime()).isEqualTo(LocalTime.of(9, 45)),
                    () -> assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.출석)
            );
        }
        
        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 3);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 3);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var targetDate = LocalDate.of(2024, 12, 3);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(targetDate, newTime);
            
            //then
            assertThat(getAttendanceOf(targetDate).getStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @Test
        void _8시_이전으로_수정하려고하면_예외가_발생한다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 5);
            var oldTime = LocalTime.of(10, 10);
            var newTime = LocalTime.of(7, 59);
            sut.attend(targetDate, oldTime);
            
            //expected
            assertThatThrownBy(() -> sut.modify(targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void _11시_이후로_수정하려고하면_예외가_발생한다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 5);
            var oldTime = LocalTime.of(10, 10);
            var newTime = LocalTime.of(23, 1);
            sut.attend(targetDate, oldTime);
            
            //expected
            assertThatThrownBy(() -> sut.modify(targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void 출석_가능한_달이_아니면_예외가_발생한다() {
            //given
            var targetDate = LocalDate.of(2024, 11, 1);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        @Test
        void 주말이라면_예외가_발생한다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 1);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        @Test
        void 공휴일이라면_예외가_발생한다() {
            //given
            var targetDate = LocalDate.of(2024, 12, 25);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        private Attendance getAttendanceOf(final LocalDate date) {
            return sut.getAllAttendances().stream()
                    .filter(attendance -> attendance.getAttendDate() == date)
                    .findFirst()
                    .orElse(null);
        }
    }
    
    @Nested
    class 출석_기록_확인_테스트 {
        
        @Test
        void 출석_기록을_확인할_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            
            sut.attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 3));
            sut.attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 15));
            sut.attend(LocalDate.of(2024, 12, 6), LocalTime.of(10, 40));
            sut.attend(LocalDate.of(2024, 12, 9), LocalTime.of(13, 3));
            sut.attend(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.getAllAttendances();

            //then
            assertAll(
                    () -> assertThat(result).extracting(
                            "attendDate", "status"
                    ).containsExactlyInAnyOrder(
                            Tuple.tuple(LocalDate.of(2024, 12, 2), AttendanceStatus.출석),
                            Tuple.tuple(LocalDate.of(2024, 12, 3), AttendanceStatus.출석),
                            Tuple.tuple(LocalDate.of(2024, 12, 4), AttendanceStatus.결석),
                            Tuple.tuple(LocalDate.of(2024, 12, 5), AttendanceStatus.지각),
                            Tuple.tuple(LocalDate.of(2024, 12, 6), AttendanceStatus.결석),
                            Tuple.tuple(LocalDate.of(2024, 12, 9), AttendanceStatus.출석),
                            Tuple.tuple(LocalDate.of(2024, 12, 10), AttendanceStatus.지각)
                    )
            );
        }
        
        @Test
        void 출석_기록에_주말은_포함되지_않는다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            
            //when
            var result = sut.getAllAttendances();

            //then
            assertThat(result).extracting(
                    "attendDate"
            ).doesNotContain(
                    LocalDate.of(2024, 12, 1),
                    LocalDate.of(2024, 12, 7),
                    LocalDate.of(2024, 12, 8)
            );
        }
        
        @Test
        void 출석_기록에_공휴일은_포함되지_않는다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 30));
            
            //when
            var result = sut.getAllAttendances();

            //then
            assertThat(result).extracting(
                    "attendDate"
            ).doesNotContain(
                    LocalDate.of(2024, 12, 25)
            );
        }
    }
    
    @Nested
    class 제적_위험도_확인_테스트 {
        
        @Test
        void 제적_위험도를_확인할_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            
            sut.attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 3));
            sut.attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 15));
            sut.attend(LocalDate.of(2024, 12, 6), LocalTime.of(10, 40));
            sut.attend(LocalDate.of(2024, 12, 9), LocalTime.of(13, 3));
            sut.attend(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.getExpelWarning();

            //then
            assertAll(
                    () -> assertThat(result).isEqualTo(ExpelWarning.경고)
            );
        }
    }
    
    @Nested
    class 출석_상태_확인_테스트 {
        
        @Test
        void 특정_날짜의_출석_상태를_확인할_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            var attendDate = LocalDate.of(2024, 12, 2);
            sut.attend(attendDate, LocalTime.of(13, 0));
            
            //when
            var result = sut.getAttendanceStatusOf(attendDate);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.출석);
        }
    }
    
    @Nested
    class 특정_출석_상태_개수_확인_테스트 {
        
        @Test
        void 출석_개수를_셀_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            sut.attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(AttendanceStatus.출석);
            
            //then
            assertThat(result).isEqualTo(4);
        }
        
        @Test
        void 지각_개수를_셀_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            sut.attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(AttendanceStatus.지각);
            
            //then
            assertThat(result).isEqualTo(2);
        }
        
        @Test
        void 결석_개수를_셀_수_있다() {
            //given
            var sut = new CrewAttendance(LocalDate.of(2024, 12, 10));
            sut.attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(LocalDate.of(2024, 12, 9), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(AttendanceStatus.결석);
            
            //then
            assertThat(result).isEqualTo(1);
        }
    }
    
}
