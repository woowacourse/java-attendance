package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentPunishmentTest {
    @Test
    @DisplayName("결석이 5회 이상이면 제적 리턴 테스트")
    void test1() {
        Assertions.assertThat(StudentPunishment.determineDisciplinaryAction(5))
                .isEqualTo(StudentPunishment.DISMISSAL);
    }

    @Test
    @DisplayName("결석이 3회 이상이면 면담 리턴 테스트")
    void test2() {
        Assertions.assertThat(StudentPunishment.determineDisciplinaryAction(3))
                .isEqualTo(StudentPunishment.INTERVIEW);
    }

    @Test
    @DisplayName("결석이 2회 이상이면 경고 리턴 테스트")
    void test3() {
        Assertions.assertThat(StudentPunishment.determineDisciplinaryAction(2))
                .isEqualTo(StudentPunishment.WARNING);
    }

}