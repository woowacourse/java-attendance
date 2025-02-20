import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendCountTest {

    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 정상: 무결")
    void test10() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 0, 0);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.CLEAR);
    }
    
    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 정상")
    void test13() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 2, 0);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.CLEAR);
    }

    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 정상 지각:2,결석:1")
    void test11() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 2, 1);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.CLEAR);
    }

    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 경고")
    void test() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 0, 2);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.WARNING);
    }

    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 면담")
    void test2() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 0, 3);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.INTERVIEW);
    }

    @Test
    @DisplayName("제적 위험자를 계산하는 기능 - 제적")
    void test3() throws Exception {
        //given
        AttendCount attendCount = new AttendCount(0, 0, 6);

        //when
        WarningStatus warningStatus = attendCount.judgeWarning();

        //then
        Assertions.assertThat(warningStatus).isEqualTo(WarningStatus.EXPEL);
    }
}
