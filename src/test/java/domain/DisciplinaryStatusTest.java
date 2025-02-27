package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DisciplinaryStatusTest {

    @Test
    @DisplayName("결석, 지각 횟수를 입력받아 학생의 정상 상태를 반환")
    void normalDisciplinaryStatusTest() {
        // given
        int lateCount = 3;
        int absenceCount = 0;

        // when
        DisciplinaryStatus status = DisciplinaryStatus.from(absenceCount, lateCount);

        // then
        assertThat(status).isEqualTo(DisciplinaryStatus.NORMAL);
    }

    @Test
    @DisplayName("결석, 지각 횟수를 입력받아 학생의 경고 상태를 반환")
    void warnedDisciplinaryStatusTest() {
        // given
        int lateCount = 3;
        int absenceCount = 1;

        // when
        DisciplinaryStatus status = DisciplinaryStatus.from(absenceCount, lateCount);

        // then
        assertThat(status).isEqualTo(DisciplinaryStatus.WARNED);
    }

    @Test
    @DisplayName("결석, 지각 횟수를 입력받아 학생의 면담 상태를 반환")
    void counselingDisciplinaryStatusTest() {
        // given
        int lateCount = 2;
        int absenceCount = 3;

        // when
        DisciplinaryStatus status = DisciplinaryStatus.from(absenceCount, lateCount);

        // then
        assertThat(status).isEqualTo(DisciplinaryStatus.COUNSELING);
    }

    @Test
    @DisplayName("결석, 지각 횟수를 입력받아 학생의 제적 상태를 반환")
    void dismissedDisciplinaryStatusTest() {
        // given
        int lateCount = 3;
        int absenceCount = 5;

        // when
        DisciplinaryStatus status = DisciplinaryStatus.from(absenceCount, lateCount);

        // then
        assertThat(status).isEqualTo(DisciplinaryStatus.DISMISSED);
    }
}
