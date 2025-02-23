package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DisciplinaryStatusTest {
    @Test
    @DisplayName("결석 2회면 경고를 반환한다.")
    public void getWarningTest() {
        // given & when
        DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.getStatus(1, 3);
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.WARNING);
    }

    @Test
    @DisplayName("결석 3회 이상 5회 이하면 면담을 반환한다.")
    public void getOneOnOneTest() {
        // given & when
        DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.getStatus(2, 4);
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.ONE_ON_ONE);
    }

    @Test
    @DisplayName("결석 6회 이상이면 제적을 반환한다.")
    public void getExpelledTest() {
        // given & when
        DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.getStatus(6, 5);
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.EXPELLED);
    }

    @Test
    @DisplayName("결석 1회 이하면 해당 사항 없음을 반환한다.")
    public void getNoneTest() {
        // given & when
        DisciplinaryStatus disciplinaryStatus = DisciplinaryStatus.getStatus(0, 4);
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.NONE);
    }
}
