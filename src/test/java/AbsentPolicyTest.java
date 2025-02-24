import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/*
// 테스트 코드 순서
1. 주말일 경우 예외 발생 [x]
2. 공휴일일 경우 예외 발생 [x]
3. 모든 시간 결석 처리 [x]
4. 시작 시간부터 5분 내면 출석(10시)
    4-1 월요일일 경우 13시
5. 시작 시간부터 30분 내면 지각
    5-1 월요일일 경우 13시
 */
public class AbsentPolicyTest {
    @ParameterizedTest
    @DisplayName("주말에 출석하려고 하는 경우 예외가 발생한다")
    @ValueSource(ints = {7,8})
    public void validateWeekendTest(int dayOfMonth){
        //given
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalDate attendanceDate = LocalDate.of(2024,12,dayOfMonth);

        //when-then
        assertThatThrownBy(() -> absentPolicy.validateIsWeekend(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공휴일에 출석하려고 하는 경우 예외가 발생한다")
    public void validateHolidayTest(){
        //given
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalDate attendanceDate = LocalDate.of(2024,12,25);

        //when-then
        assertThatThrownBy(() -> absentPolicy.validateIsHoliday(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("결석 처리를 할 수 있다")
    public void checkAbsentStatusTest(){
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalDateTime educationDateTime = LocalDateTime.of(2024,12,10,10,31);

        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo("결석");
    }

    @Test
    @DisplayName("시작 시간부터 5분 이하이면 출석이다")
    public void checkAttendanceStatusTest(){
        AbsentPolicy absentPolicy = new AbsentPolicy();
        LocalDateTime educationDateTime = LocalDateTime.of(2024,12,10,10,5);

        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo("출석");
    }

//    @Test
//    @DisplayName("교육 시작 시간으로부터 5분 초과는 지각으로 간주한다")
//    public void checkAttendanceStatusTest() {
//        //given
//        AbsentPolicy absentPolicy = new AbsentPolicy();
//        LocalDateTime educationDateTime = LocalDateTime.of(2024,12,10,10,6);
//
//        //when-then
//        assertThat(absentPolicy.checkAttendanceStatus(educationDateTime)).isEqualTo("지각");
//    }
}
