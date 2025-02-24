import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/*
// 테스트 코드 순서
1. 주말일 경우 예외 발생
2. 공휴일일 경우 예외 발생
3. 모든 시간 결석 처리
4. 시작 시간부터 5분 내면 출석(10시)
    4-1 월요일일 경우 13시
5. 시작 시간부터 30분 내면 지각
    5-1 월요일일 경우 13시
 */
public class AbsentPolicyTest {
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
