package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AbsencePolicyTest {

    @DisplayName("지각과 결석 횟수를 입력받아 제적 위험자를 확인할 수 있다.")
    @Test
    void getAbsencePolicy() {
        //given
        int lateCount = 4;
        int absenceCount = 2;

        //when
        String actual = AbsencePolicy.getAbsencePolicy(absenceCount, lateCount);

        //then
        Assertions.assertThat(actual).isEqualTo("면담");
    }
}
