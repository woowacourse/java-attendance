package domain;

import attendance.domain.WarningLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WarningLevelTest {

    @Test
    void 결석이_2회_이상이라면_경고_대상자이다() {
        //given
        int lateCount = 0;
        int absentCount = 2;

        //when
        WarningLevel result = WarningLevel.from(lateCount, absentCount);

        //then
        Assertions.assertThat(result).isEqualTo(WarningLevel.WARNING);
    }
}
