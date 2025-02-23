package domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceBookTest {
    
    private final List<String> crews = List.of("dompoo", "lisa", "neo");
    
    @Nested
    class 생성_테스트 {
        
        private final LocalDate weekend = LocalDate.of(2025, 2, 23);
        private final LocalDate holiday = LocalDate.of(2024, 12, 25);
        
        @Test
        void 주말로_생성하려고_하면_예외가_발생한다() {
            //given
            
            //expected
            assertThatThrownBy(() -> new AttendanceBook(crews, weekend))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주말에는 출석할 수 없습니다.");
        }
        
        @Test
        void 공휴일로_생성하려고_하면_예외가_발생한다() {
            //given
            
            //expected
            assertThatThrownBy(() -> new AttendanceBook(crews, holiday))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("공휴일에는 출석할 수 없습니다.");
        }
    }
    
    @Nested
    class 출석_확인_테스트 {
        
        private final LocalDate notMonday = LocalDate.of(2025, 2, 25);
        private final LocalDate monday = LocalDate.of(2025, 2, 24);
        
        @Test
        void 닉네임과_시간을_입력하여_출석한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(10, 5);
            var sut = new AttendanceBook(crews, notMonday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result).extracting(
                    "attendDate", "attendTime", "status"
            ).containsExactly(
                    LocalDate.of(2025, 2, 25), LocalTime.of(10, 5), AttendanceStatus.출석
            );
        }
        
        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            
            var sut = new AttendanceBook(crews, monday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            var sut = new AttendanceBook(crews, monday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            var sut = new AttendanceBook(crews, monday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            var sut = new AttendanceBook(crews, notMonday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            var sut = new AttendanceBook(crews, notMonday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석이다(String timeValue) {
            //given
            var nickname = "dompoo";
            var time = LocalTime.parse(timeValue);
            var sut = new AttendanceBook(crews, notMonday);
            
            //when
            var result = sut.attend(nickname, time);
            
            //then
            assertThat(result.status()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"07:56", "07:57", "07:58", "07:59"})
        void _8시_이전으로_출석하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(7, 59);
            var sut = new AttendanceBook(crews, LocalDate.of(2025, 2, 25));
            
            //expected
            assertThatThrownBy(() -> sut.attend(nickname, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @ParameterizedTest
        @CsvSource({"23:01", "23:02", "23:03", "23:04"})
        void _23시_이후로_출석하려고하면_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(7, 59);
            var sut = new AttendanceBook(crews, LocalDate.of(2025, 2, 25));
            
            //expected
            assertThatThrownBy(() -> sut.attend(nickname, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void 존재하지_않는_닉네임이면_예외가_발생한다() {
            //given
            var nickname = "brown";
            var time = LocalTime.of(10, 5);
            var sut = new AttendanceBook(crews, LocalDate.of(2025, 2, 25));
            
            //expected
            assertThatThrownBy(() -> sut.attend(nickname, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("등록되지 않은 닉네임입니다.");
        }
        
        @Test
        void 이미_출석한_경우_예외가_발생한다() {
            //given
            var nickname = "dompoo";
            var time = LocalTime.of(10, 5);
            var sut = new AttendanceBook(crews, LocalDate.of(2025, 2, 25));
            
            //when
            sut.attend(nickname, time);
            
            //then
            assertThatThrownBy(() -> sut.attend(nickname, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("하루에 여러번 출석할 수 없습니다. 수정 기능을 이용하세요.");
        }
    }
    
}
