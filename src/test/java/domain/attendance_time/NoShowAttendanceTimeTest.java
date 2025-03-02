package domain.attendance_time;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NoShowAttendanceTimeTest {
    
    @Test
    void 생성하면_시간은_비어있다() {
        //given
        
        //when
        var result = new NoShowAttendanceTime();
        
        //then
        assertThat(result.getAttendTime()).isEmpty();
    }
}
