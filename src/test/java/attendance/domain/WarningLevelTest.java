package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class WarningLevelTest {

    @Test
    void 결석이_2회_이상이라면_경고_대상자이다() {
        //given
        int lateCount = 0;
        int absentCount = 2;

        //when
        WarningLevel result = WarningLevel.of(lateCount, absentCount);

        //then
        assertThat(result).isEqualTo(WarningLevel.WARNING);
    }

    @Test
    void 결석이_3회_이상이라면_면담_대상자이다() {
        //given
        int lateCount = 0;
        int absentCount = 3;

        //when
        WarningLevel result = WarningLevel.of(lateCount, absentCount);

        //then
        assertThat(result).isEqualTo(WarningLevel.INTERVIEW);
    }

    @Test
    void 결석이_5회_초과라면_제적_대상자이다() {
        //given
        int lateCount = 0;
        int absentCount = 6;

        //when
        WarningLevel result = WarningLevel.of(lateCount, absentCount);

        //then
        assertThat(result).isEqualTo(WarningLevel.WEEDING);
    }

    @Test
    void 결석이_2회_미만이라면_NONE_대상자이다() {
        //given
        int lateCount = 0;
        int absentCount = 1;

        //when
        WarningLevel result = WarningLevel.of(lateCount, absentCount);

        //then
        assertThat(result).isEqualTo(WarningLevel.NONE);
    }
}
