package attendance.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WarningTest {

    @Test
    void 크루가_2번_결석한_경우_경고를_받는다() {
        //given
        long absenceCount = 2;

        //when
        AttendanceWarning warning = AttendanceWarning.from(absenceCount);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.WARNING);
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 4, 5})
    void 크루가_3번에서_5번_결석한_경우_면담를_받는다(long absenceCount) {

        //when
        AttendanceWarning warning = AttendanceWarning.from(absenceCount);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.NEED_MEETING);
    }

    @Test
    void 크루가_6이상_결석할_경우_제적이다() {
        //given
        long absenceCount = 6;

        //when
        AttendanceWarning warning = AttendanceWarning.from(absenceCount);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.OUT);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    void 크루가_2번_미만_결석한_경우_해당없음이다(long absenceCount) {

        //when
        AttendanceWarning warning = AttendanceWarning.from(absenceCount);

        //then
        Assertions.assertThat(warning).isEqualTo(AttendanceWarning.NONE);
    }
}
