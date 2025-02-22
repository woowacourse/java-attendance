package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

class AttendanceStatusTest {
    
    @Nested
    class 생성_테스트 {
        
        @ParameterizedTest
        @ValueSource(ints = {-2, -1, 0, 1, 2, 3, 4, 5})
        void 타겟_시간_이후_5분_까지는_출석이다(int difference) {
            //given
            LocalTime targetTime = LocalTime.of(10, 10);
            LocalTime attendTime = LocalTime.of(10, 10 + difference);
            
            //when
            AttendanceStatus result = AttendanceStatus.of(targetTime, attendTime);
            
            //then
            Assertions.assertThat(result).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @ValueSource(ints = {6, 7, 8, 9, 27, 28, 29, 30})
        void 타겟_시간_이후_6분부터_30분_까지는_지각이다(int difference) {
            //given
            LocalTime targetTime = LocalTime.of(10, 10);
            LocalTime attendTime = LocalTime.of(10, 10 + difference);
            
            //when
            AttendanceStatus result = AttendanceStatus.of(targetTime, attendTime);
            
            //then
            Assertions.assertThat(result).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @ValueSource(ints = {31, 32, 33, 34})
        void 타겟_시간_이후_31부터는_결석이다(int difference) {
            //given
            LocalTime targetTime = LocalTime.of(10, 10);
            LocalTime attendTime = LocalTime.of(10, 10 + difference);
            
            //when
            AttendanceStatus result = AttendanceStatus.of(targetTime, attendTime);
            
            //then
            Assertions.assertThat(result).isEqualTo(AttendanceStatus.결석);
        }
    }
    
}