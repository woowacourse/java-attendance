package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentPunishmentTest {
    @Test
    @DisplayName("제적 결정 테스트")
    void test1() {
        Assertions.assertEquals(
                StudentPunishment.calculatePunishment(5), StudentPunishment.ABSENT
        );
    }

    @Test
    @DisplayName("면담 결정 테스트")
    void test2() {
        Assertions.assertEquals(
                StudentPunishment.calculatePunishment(3), StudentPunishment.INTERVIEW
        );
    }

    @Test
    @DisplayName("경고 결정 테스트")
    void test3() {
        Assertions.assertEquals(
                StudentPunishment.calculatePunishment(2), StudentPunishment.WARNING
        );
    }
}
