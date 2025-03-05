package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DismissalTest {

    @DisplayName("지각 3회는 결석 1회로 간주한다.")
    @Test
    void threeLateCountEqualOneAbsenceCount() {
        //given
        int lateCount = 3;
        int absenceCount = 1;

        //when
        Dismissal actual = Dismissal.findDismissalBy(lateCount, absenceCount);

        //then
        assertThat(actual).isEqualTo(Dismissal.WARNING);
    }

    @DisplayName("결석이 2회 이상이라면 경고 대상자다.")
    @Test
    void warning() {
        //given

        int lateCount = 1;
        int absenceCount = 2;

        //when
        Dismissal actual = Dismissal.findDismissalBy(lateCount, absenceCount);

        //then
        assertThat(actual).isEqualTo(Dismissal.WARNING);
    }

    @DisplayName("결석이 3회 이상이라면 면담 대상자다.")
    @Test
    void interview() {
        //given
        int lateCount = 1;
        int absenceCount = 3;

        //when
        Dismissal actual = Dismissal.findDismissalBy(lateCount, absenceCount);

        //then
        assertThat(actual).isEqualTo(Dismissal.INTERVIEW);
    }

    @DisplayName("결석이 5회 이상이라면 제적 대상자다.")
    @Test
    void dismissal() {
        //given
        int lateCount = 1;
        int absenceCount = 5;

        //when
        Dismissal actual = Dismissal.findDismissalBy(lateCount, absenceCount);

        //then
        assertThat(actual).isEqualTo(Dismissal.DISMISSAL);
    }
}
