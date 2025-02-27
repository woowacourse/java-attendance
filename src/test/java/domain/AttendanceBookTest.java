package domain;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AttendanceBookTest {

    private final List<String> crews = List.of("dompoo", "lisa", "neo");
    private AttendanceBook sut;

    @BeforeEach
    void setUp() {
        sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 30));
    }

    @Nested
    class 출석_확인_테스트 {

        private final LocalDate notMonday = LocalDate.of(2024, 12, 3);
        private final LocalDate monday = LocalDate.of(2024, 12, 2);

        @Test
        void 닉네임과_시간을_입력하여_출석한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(10, 5);
            
            //expected
            assertThatCode(() -> sut.attend(nickname, notMonday, time))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, monday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, monday)).isEqualTo(AttendanceStatus.출석);
        }

        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, monday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, monday)).isEqualTo(AttendanceStatus.지각);
        }

        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, monday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, monday)).isEqualTo(AttendanceStatus.결석);
        }

        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, notMonday)).isEqualTo(AttendanceStatus.출석);
        }

        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, notMonday)).isEqualTo(AttendanceStatus.지각);
        }

        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, notMonday)).isEqualTo(AttendanceStatus.결석);
        }

        @ParameterizedTest
        @CsvSource({"07:56", "07:57", "07:58", "07:59"})
        void _8시_이전으로_출석하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(7, 59);

            //expected
            assertThatThrownBy(() -> sut.attend(nickname, notMonday, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }

        @ParameterizedTest
        @CsvSource({"23:01", "23:02", "23:03", "23:04"})
        void _23시_이후로_출석하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(7, 59);

            //expected
            assertThatThrownBy(() -> sut.attend(nickname, notMonday, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }

        @Test
        void 주말에_출석하려고_하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var weekend = LocalDate.of(2024, 12, 1);
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(nickname, weekend, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }

        @Test
        void 공휴일에_출석하려고_하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var holiday = LocalDate.of(2024, 12, 25);
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(nickname, holiday, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }

        @Test
        void 존재하지_않는_닉네임이면_예외가_발생한다() {
            //given
            var nickname = "brown";
            var time = LocalTime.of(10, 5);

            //expected
            assertThatThrownBy(() -> sut.attend(nickname, notMonday, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }
    }

    @Nested
    class 출석_수정_테스트 {

        @Test
        void 닉네임_날짜_원하는_날짜를_통해_출석을_수정할_수_있다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var oldTime = LocalTime.of(10, 10);
            var newTime = LocalTime.of(9, 45);
            sut.attend(nickname, targetDate, oldTime);
            
            //expected
            assertThatCode(() -> sut.attend(nickname, targetDate, newTime))
                    .doesNotThrowAnyException();
        }

        @Test
        void 기존에_출석하지_않았어도_수정할_수_있다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatCode(() -> sut.attend(nickname, targetDate, newTime))
                    .doesNotThrowAnyException();
        }

        @Test
        void 존재하지_않는_닉네임이라면_예외가_발생한다() {
            //given
            var nickname = "brown";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }
        
        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);

            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(sut.getAttendanceStatusOf(nickname, targetDate)).isEqualTo(AttendanceStatus.결석);
        }
        
        @Test
        void _8시_이전으로_수정하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(7, 59);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void _11시_이후로_수정하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(23, 1);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void 출석_가능한_달이_아니면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 11, 1);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        @Test
        void 주말이라면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 1);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
        
        @Test
        void 공휴일이라면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 25);
            var newTime = LocalTime.of(9, 45);
            
            //expected
            assertThatThrownBy(() -> sut.modify(nickname, targetDate, newTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석할 수 없는 날짜입니다.");
        }
    }
    
    @Nested
    class 크루별_출석_기록_확인_테스트 {
        
        @Test
        void 크루별_출석_기록을_확인할_수_있다() {
            //given
            var nickname = "dompoo";
            sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 10));
            sut.attend("dompoo", LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend("dompoo", LocalDate.of(2024, 12, 3), LocalTime.of(10, 3));
            sut.attend("dompoo", LocalDate.of(2024, 12, 5), LocalTime.of(10, 15));
            sut.attend("dompoo", LocalDate.of(2024, 12, 6), LocalTime.of(10, 40));
            sut.attend("dompoo", LocalDate.of(2024, 12, 9), LocalTime.of(13, 3));
            sut.attend("dompoo", LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.getAllAttendnaces(nickname);

            //then
            assertThat(result).extracting(
                    "attendDate", "status"
            ).containsExactlyInAnyOrder(
                    Tuple.tuple(LocalDate.of(2024, 12, 2), AttendanceStatus.출석),
                    Tuple.tuple(LocalDate.of(2024, 12, 3), AttendanceStatus.출석),
                    Tuple.tuple(LocalDate.of(2024, 12, 4), AttendanceStatus.결석),
                    Tuple.tuple(LocalDate.of(2024, 12, 5), AttendanceStatus.지각),
                    Tuple.tuple(LocalDate.of(2024, 12, 6), AttendanceStatus.결석),
                    Tuple.tuple(LocalDate.of(2024, 12, 9), AttendanceStatus.출석),
                    Tuple.tuple(LocalDate.of(2024, 12, 10), AttendanceStatus.지각)
            );
        }
        
        @Test
        void 크루별_출석_기록에_주말은_포함되지_않는다() {
            //given
            var nickname = "dompoo";
            
            //when
            var result = sut.getAllAttendnaces(nickname);

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
        void 크루별_출석_기록에_공휴일은_포함되지_않는다() {
            //given
            var nickname = "dompoo";
            
            //when
            var result = sut.getAllAttendnaces(nickname);

            //then
            assertThat(result).extracting(
                    "attendDate"
            ).doesNotContain(
                    LocalDate.of(2024, 12, 25)
            );
        }
        
        @Test
        void 존재하지_않는_닉네임이면_예외가_발생한다() {
            //given
            var nickname = "brown";
            
            //expected
            assertThatThrownBy(() -> sut.getAllAttendnaces(nickname))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }
    }
    
    @Nested
    class 제적_위험자_확인 {
        
        @Test
        void 전체_제적_위험자를_확인한다() {
            //given
            var sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 5));
            sut.attend("dompoo", LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend("dompoo", LocalDate.of(2024, 12, 3), LocalTime.of(10, 15));
            sut.attend("dompoo", LocalDate.of(2024, 12, 4), LocalTime.of(10, 15));
            sut.attend("dompoo", LocalDate.of(2024, 12, 5), LocalTime.of(10, 45));
            sut.attend("lisa", LocalDate.of(2024, 12, 2), LocalTime.of(13, 45));
            sut.attend("lisa", LocalDate.of(2024, 12, 3), LocalTime.of(10, 15));
            sut.attend("lisa", LocalDate.of(2024, 12, 4), LocalTime.of(10, 15));
            sut.attend("lisa", LocalDate.of(2024, 12, 5), LocalTime.of(10, 45));
            sut.attend("neo", LocalDate.of(2024, 12, 2), LocalTime.of(13, 45));
            sut.attend("neo", LocalDate.of(2024, 12, 3), LocalTime.of(10, 45));
            sut.attend("neo", LocalDate.of(2024, 12, 4), LocalTime.of(10, 15));
            sut.attend("neo", LocalDate.of(2024, 12, 5), LocalTime.of(10, 45));
            
            //when
            var result = sut.getExpelWarnings();

            //then
            assertThat(result.entrySet()).containsExactlyInAnyOrder(
                    entry("lisa", ExpelWarning.경고),
                    entry("neo", ExpelWarning.면담)
            );
        }
        
        @Test
        void 정상_범위의_크루는_포함되지_않는다() {
            //given
            var sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 5));
            sut.attend("dompoo", LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend("dompoo", LocalDate.of(2024, 12, 3), LocalTime.of(10, 15));
            sut.attend("dompoo", LocalDate.of(2024, 12, 4), LocalTime.of(10, 15));
            sut.attend("dompoo", LocalDate.of(2024, 12, 5), LocalTime.of(10, 45));
            
            //when
            var result = sut.getExpelWarnings();

            //then
            assertThat(result.keySet()).doesNotContain("dompoo");
        }
    }
    
    @Nested
    class 특정_출석_상태_개수_세기_테스트 {
        
        @Test
        void 출석_개수를_셀_수_있다() {
            //given
            var nickname = "dompoo";
            var sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 10));
            sut.attend(nickname, LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(nickname, LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(nickname, AttendanceStatus.출석);
            
            //then
            assertThat(result).isEqualTo(4);
        }
        
        @Test
        void 지각_개수를_셀_수_있다() {
            //given
            var nickname = "dompoo";
            var sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 10));
            sut.attend(nickname, LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(nickname, LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(nickname, AttendanceStatus.지각);
            
            //then
            assertThat(result).isEqualTo(2);
        }
        
        @Test
        void 결석_개수를_셀_수_있다() {
            //given
            var nickname = "dompoo";
            var sut = new AttendanceBook(crews, LocalDate.of(2024, 12, 10));
            sut.attend(nickname, LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 3), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 4), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 5), LocalTime.of(10, 0));
            sut.attend(nickname, LocalDate.of(2024, 12, 6), LocalTime.of(10, 15));
            sut.attend(nickname, LocalDate.of(2024, 12, 10), LocalTime.of(10, 15));
            
            //when
            var result = sut.countAttendanceStatusOf(nickname, AttendanceStatus.결석);
            
            //then
            assertThat(result).isEqualTo(1);
        }
    }

}
