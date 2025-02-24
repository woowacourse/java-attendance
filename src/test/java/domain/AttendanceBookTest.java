package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
            
            //when
            var result = sut.attend(nickname, notMonday, time);
            
            //then
            assertAll(
                    () -> assertThat(result.attendDate()).isEqualTo(LocalDate.of(2024, 12, 3)),
                    () -> assertThat(result.attendTime()).isEqualTo(LocalTime.of(10, 5)),
                    () -> assertThat(result.status()).isEqualTo(AttendanceStatus.출석)
            );
        }
        
        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, monday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, monday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, monday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = sut.attend(nickname, notMonday, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.결석);
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
                    .hasMessage("주말에는 출석할 수 없습니다.");
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
                    .hasMessage("공휴일에는 출석할 수 없습니다.");
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
        
        @Test
        void 이미_출석한_경우_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(10, 5);
            
            //when
            sut.attend(nickname, notMonday, time);
            
            //then
            assertThatThrownBy(() -> sut.attend(nickname, notMonday, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("하루에 여러번 출석할 수 없습니다. 수정 기능을 이용하세요.");
        }
    }
    
    @Nested
    class 출석_수정_테스트 {
        
        @Test
        void 출석을_수정하면_이전_기록과_바뀐_기록이_나온다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var oldTime = LocalTime.of(10, 10);
            var newTime = LocalTime.of(9, 45);
            sut.attend(nickname, targetDate, oldTime);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertAll(
                    () -> assertThat(result.date()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(result.oldAttendTime()).isEqualTo(Optional.of(LocalTime.of(10, 10))),
                    () -> assertThat(result.oldStatus()).isEqualTo(Optional.of(AttendanceStatus.지각)),
                    () -> assertThat(result.newAttendTime()).isEqualTo(LocalTime.of(9, 45)),
                    () -> assertThat(result.newStatus()).isEqualTo(AttendanceStatus.출석)
            );
        }
        
        @Test
        void 기존에_출석하지_않았어도_수정할_수_있다() {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.of(9, 45);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertAll(
                    () -> assertThat(result.date()).isEqualTo(LocalDate.of(2024, 12, 5)),
                    () -> assertThat(result.oldAttendTime()).isEqualTo(Optional.empty()),
                    () -> assertThat(result.oldStatus()).isEqualTo(Optional.empty()),
                    () -> assertThat(result.newAttendTime()).isEqualTo(LocalTime.of(9, 45)),
                    () -> assertThat(result.newStatus()).isEqualTo(AttendanceStatus.출석)
            );
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
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 2);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석으로_수정된다(String timeValue) {
            //given
            var nickname = "dompoo";
            var targetDate = LocalDate.of(2024, 12, 5);
            var newTime = LocalTime.parse(timeValue);
            
            //when
            var result = sut.modify(nickname, targetDate, newTime);
            
            //then
            assertThat(result.newStatus()).isEqualTo(AttendanceStatus.결석);
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
                    .hasMessage("주말에는 출석할 수 없습니다.");
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
                    .hasMessage("공휴일에는 출석할 수 없습니다.");
        }
    }
}
